param(
    [string]$SpriteDumpDir = "C:\Users\Jakob\OneDrive\Desktop\Elvarg Rebirth\New Elvarg\Source\Elvarg-Rebirth_Server\sprite_dump\index8_decoded"
)

$ErrorActionPreference = "Stop"

Add-Type -AssemblyName System.Drawing

$resourceDir = Join-Path $PSScriptRoot "..\game\src\main\resources\arceuus"
$sheetPath = Join-Path $resourceDir "arceuus_spells.png"
$spellDir = Join-Path $resourceDir "spells"
$spellOffDir = Join-Path $spellDir "off"
$tempSpellDir = Join-Path $spellDir "Temp Spells"
$bookPath = Join-Path $resourceDir "arceuus_spellbook.png"

$cellSize = 33
$iconDrawSize = 20
$bookSpriteId = 6390

# Rows 1-4 are frozen. Rows 5-9 are sourced directly from the row folders under Temp Spells.
$spellSources = @(
    @(2057, 2058, 2052, 2060, 2061),
    @(2063, 1257, 1267, 1270, 1271),
    @(1300, 2059, 2053, 2079, 1315),
    @(1261, 2992, 1268, 2995, 2997),
    @(
        (Join-Path $tempSpellDir "row 5\1.png"),
        (Join-Path $tempSpellDir "row 5\2.png"),
        (Join-Path $tempSpellDir "row 5\3.png"),
        (Join-Path $tempSpellDir "row 5\4.png"),
        (Join-Path $tempSpellDir "row 5\5.png")
    ),
    @(
        (Join-Path $tempSpellDir "row 6\1.png"),
        (Join-Path $tempSpellDir "row 6\2.png"),
        (Join-Path $tempSpellDir "row 6\3.png"),
        (Join-Path $tempSpellDir "row 6\4.png"),
        (Join-Path $tempSpellDir "row 6\5.png")
    ),
    @(
        (Join-Path $tempSpellDir "row 7\1.png"),
        (Join-Path $tempSpellDir "row 7\2.png"),
        (Join-Path $tempSpellDir "row 7\3.png"),
        (Join-Path $tempSpellDir "row 7\4.png"),
        (Join-Path $tempSpellDir "row 7\5.png")
    ),
    @(
        (Join-Path $tempSpellDir "Row 8\1.png"),
        (Join-Path $tempSpellDir "Row 8\2.png"),
        (Join-Path $tempSpellDir "Row 8\3.png"),
        (Join-Path $tempSpellDir "Row 8\4.png"),
        (Join-Path $tempSpellDir "Row 8\5.png")
    ),
    @(
        (Join-Path $tempSpellDir "row 9\1.png"),
        (Join-Path $tempSpellDir "row 9\2.png"),
        (Join-Path $tempSpellDir "row 9\3.png"),
        (Join-Path $tempSpellDir "row 9\4.png"),
        (Join-Path $tempSpellDir "row 9\5.png")
    )
)

function New-CellBitmap {
    param($SpriteSource)

    $cell = New-Object System.Drawing.Bitmap $cellSize, $cellSize, ([System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    for ($x = 0; $x -lt $cellSize; $x++) {
        for ($y = 0; $y -lt $cellSize; $y++) {
            $cell.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(0, 0, 0, 0))
        }
    }

    if ($null -ne $SpriteSource) {
        if ($SpriteSource -is [string]) {
            $path = $SpriteSource
        } else {
            $path = Join-Path $SpriteDumpDir ($SpriteSource.ToString() + ".png")
        }
        if (-not (Test-Path $path)) {
            throw "Missing sprite file: $path"
        }

        $sprite = [System.Drawing.Bitmap]::FromFile((Resolve-Path $path))
        try {
            $drawWidth = $iconDrawSize
            $drawHeight = $iconDrawSize
            $offsetX = [int][Math]::Round(($cellSize - $drawWidth) / 2.0, [MidpointRounding]::AwayFromZero)
            $offsetY = [int][Math]::Round(($cellSize - $drawHeight) / 2.0, [MidpointRounding]::AwayFromZero)

            for ($x = 0; $x -lt $drawWidth; $x++) {
                for ($y = 0; $y -lt $drawHeight; $y++) {
                    $sourceX = [Math]::Min($sprite.Width - 1, [int]([Math]::Floor($x * $sprite.Width / [double]$drawWidth)))
                    $sourceY = [Math]::Min($sprite.Height - 1, [int]([Math]::Floor($y * $sprite.Height / [double]$drawHeight)))
                    $cell.SetPixel($offsetX + $x, $offsetY + $y, $sprite.GetPixel($sourceX, $sourceY))
                }
            }
        }
        finally {
            $sprite.Dispose()
        }
    }

    return $cell
}

function Save-Png {
    param(
        [System.Drawing.Bitmap]$Bitmap,
        [string]$Path
    )

    $Bitmap.Save($Path, [System.Drawing.Imaging.ImageFormat]::Png)
}

function New-DarkCellBitmap {
    param(
        [System.Drawing.Bitmap]$Source
    )

    $dark = New-Object System.Drawing.Bitmap $Source.Width, $Source.Height, ([System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    for ($x = 0; $x -lt $Source.Width; $x++) {
        for ($y = 0; $y -lt $Source.Height; $y++) {
            $pixel = $Source.GetPixel($x, $y)
            if ($pixel.A -eq 0) {
                $dark.SetPixel($x, $y, $pixel)
                continue
            }

            $gray = [int](($pixel.R + $pixel.G + $pixel.B) / 3)
            $dim = [Math]::Max(0, [int]([Math]::Round($gray * 0.42)))
            $dark.SetPixel($x, $y, [System.Drawing.Color]::FromArgb($pixel.A, $dim, $dim, $dim))
        }
    }

    return $dark
}

New-Item -ItemType Directory -Force -Path $spellDir | Out-Null
New-Item -ItemType Directory -Force -Path $spellOffDir | Out-Null

$rows = $spellSources.Count
$cols = $spellSources[0].Count
$sheet = New-Object System.Drawing.Bitmap ($cols * $cellSize), ($rows * $cellSize), ([System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
$sheetGraphics = [System.Drawing.Graphics]::FromImage($sheet)
$sheetGraphics.Clear([System.Drawing.Color]::FromArgb(0, 0, 0, 0))

try {
    for ($row = 0; $row -lt $rows; $row++) {
        for ($col = 0; $col -lt $cols; $col++) {
            $spriteSource = $spellSources[$row][$col]
            if ($null -eq $spriteSource) {
                throw "Null sprite source at row $row column $col"
            }
            $cell = New-CellBitmap -SpriteSource $spriteSource
            try {
                $sheetGraphics.DrawImage($cell, $col * $cellSize, $row * $cellSize, $cellSize, $cellSize)
                Save-Png -Bitmap $cell -Path (Join-Path $spellDir ("r{0}_c{1}.png" -f $row, $col))
                $darkCell = New-DarkCellBitmap -Source $cell
                try {
                    Save-Png -Bitmap $darkCell -Path (Join-Path $spellOffDir ("r{0}_c{1}.png" -f $row, $col))
                }
                finally {
                    $darkCell.Dispose()
                }
            }
            finally {
                $cell.Dispose()
            }
        }
    }

    Save-Png -Bitmap $sheet -Path $sheetPath

    $book = [System.Drawing.Bitmap]::FromFile((Resolve-Path (Join-Path $SpriteDumpDir ($bookSpriteId.ToString() + ".png"))))
    try {
        Save-Png -Bitmap $book -Path $bookPath
    }
    finally {
        $book.Dispose()
    }
}
finally {
    $sheetGraphics.Dispose()
    $sheet.Dispose()
}
