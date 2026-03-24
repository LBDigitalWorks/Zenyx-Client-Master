/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.loottracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.IntToLongFunction;
import lombok.Getter;

/**
 * Represents aggregated loot from a single source (NPC name or player name).
 */
class LootTrackerRecord
{
	@Getter
	private final String title;

	@Getter
	private int kills;

	private final List<LootTrackerItem> items = new ArrayList<>();

	LootTrackerRecord(String title)
	{
		this.title = title;
	}

	/**
	 * Merges a new set of items from one kill into this record, stacking quantities.
	 */
	void addKill(Collection<LootTrackerItem> newItems)
	{
		kills++;
		outer:
		for (LootTrackerItem newItem : newItems)
		{
			for (int i = 0; i < items.size(); i++)
			{
				LootTrackerItem existing = items.get(i);
				if (existing.getId() == newItem.getId())
				{
					items.set(i, new LootTrackerItem(
						existing.getId(),
						existing.getName(),
						existing.getQuantity() + newItem.getQuantity(),
						existing.getPrice()
					));
					continue outer;
				}
			}
			items.add(newItem);
		}
	}

	void restore(int kills, Collection<LootTrackerItem> restoredItems)
	{
		this.kills = Math.max(0, kills);
		items.clear();
		if (restoredItems != null)
		{
			for (LootTrackerItem item : restoredItems)
			{
				if (item == null || item.getId() <= 0 || item.getQuantity() <= 0)
				{
					continue;
				}

				items.add(item);
			}
		}
	}

	void reprice(IntToLongFunction priceLookup)
	{
		for (int i = 0; i < items.size(); i++)
		{
			final LootTrackerItem current = items.get(i);
			final long updatedPrice = priceLookup.applyAsLong(current.getId());
			items.set(i, new LootTrackerItem(
				current.getId(),
				current.getName(),
				current.getQuantity(),
				updatedPrice
			));
		}
	}

	List<LootTrackerItem> getItems()
	{
		return Collections.unmodifiableList(items);
	}

	long getTotalPrice()
	{
		return items.stream()
			.mapToLong(i -> i.getPrice() * i.getQuantity())
			.sum();
	}
}
