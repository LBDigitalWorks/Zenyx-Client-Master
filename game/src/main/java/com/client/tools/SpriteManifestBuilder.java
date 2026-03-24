package com.client.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SpriteManifestBuilder extends JFrame {

    private JTextField inputField;
    private JTextField outputField;
    private JTextArea logArea;
    private JButton startButton;

    private final Map<String, Integer> spriteMappings = new LinkedHashMap<>();
    private int currentId = 0;

    public SpriteManifestBuilder() {
        setTitle("Sprite Manifest Builder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // === UI Layout ===
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Input folder
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputField = new JTextField();
        JButton inputBrowse = new JButton("Browse...");
        inputBrowse.addActionListener(e -> chooseFolder(inputField));
        inputPanel.add(new JLabel("Input Folder:"), BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(inputBrowse, BorderLayout.EAST);
        topPanel.add(inputPanel);

        // Output folder
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputField = new JTextField();
        JButton outputBrowse = new JButton("Browse...");
        outputBrowse.addActionListener(e -> chooseFolder(outputField));
        outputPanel.add(new JLabel("Output Folder:"), BorderLayout.WEST);
        outputPanel.add(outputField, BorderLayout.CENTER);
        outputPanel.add(outputBrowse, BorderLayout.EAST);
        topPanel.add(outputPanel);

        // Start button
        startButton = new JButton("Generate Manifest");
        startButton.addActionListener(this::onStart);
        topPanel.add(startButton);

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    private void chooseFolder(JTextField field) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void onStart(ActionEvent e) {
        String inputPath = inputField.getText().trim();
        String outputPath = outputField.getText().trim();

        if (inputPath.isEmpty() || outputPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select both input and output folders.");
            return;
        }

        File inputDir = new File(inputPath);
        File outputDir = new File(outputPath);

        if (!inputDir.exists()) {
            JOptionPane.showMessageDialog(this, "Input folder doesn't exist!");
            return;
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        startButton.setEnabled(false);
        logArea.setText("");

        new Thread(() -> generateManifest(inputDir, outputDir)).start();
    }

    private void generateManifest(File inputDir, File outputDir) {
        spriteMappings.clear();
        currentId = 0;

        try {
            log("Scanning for images...");

            List<Path> files = Files.walk(inputDir.toPath())
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".png") || p.toString().endsWith(".jpg"))
                    .sorted()
                    .collect(Collectors.toList());

            for (Path path : files) {
                String relative = inputDir.toPath().relativize(path).toString()
                        .replace("\\", "/").toLowerCase();

                try {
                    BufferedImage img = ImageIO.read(path.toFile());
                    if (img == null) continue;

                    int assignedId = currentId++;
                    spriteMappings.put(relative, assignedId);

                    String newFileName = assignedId + ".png";
                    Path outPath = outputDir.toPath().resolve(newFileName);
                    ImageIO.write(img, "png", outPath.toFile());

                    log("Mapped " + relative + " → " + newFileName);
                } catch (Exception ex) {
                    log("Failed to read: " + relative + " (" + ex.getMessage() + ")");
                }
            }

            saveManifest(outputDir);
            log("\n✅ Manifest generated successfully!");
            log("Total sprites: " + spriteMappings.size());
        } catch (Exception ex) {
            log("Error: " + ex.getMessage());
        } finally {
            SwingUtilities.invokeLater(() -> startButton.setEnabled(true));
        }
    }

    private void saveManifest(File outputDir) {
        File manifestFile = new File(outputDir, "spriteMappings.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(manifestFile)) {
            gson.toJson(spriteMappings, writer);
        } catch (IOException e) {
            log("Error saving manifest: " + e.getMessage());
        }
    }

    private void log(String text) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(text + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpriteManifestBuilder().setVisible(true));
    }
}
