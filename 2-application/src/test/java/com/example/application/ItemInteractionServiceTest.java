package com.example.application;

import com.example.application.stores.ItemStore;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.item.Consumables;
import com.example.domain.item.Weapon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ItemInteractionServiceTest {

    private ItemInteractionService itemInteractionService;
    @Mock
    private DungeonRenderer dungeonRenderer;
    @Mock
    private ItemStore itemStore;
    @Mock
    private Player player;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        itemInteractionService = new ItemInteractionService(dungeonRenderer, itemStore, player);
    }

    @Test
    public void testPickUpWeaponWhenPlayerHasNoWeapon() {
        Weapon weapon = mock(Weapon.class);
        Position playerPosition = mock(Position.class);
        Position weaponPosition = mock(Position.class);

        when(player.getPosition()).thenReturn(playerPosition);
        when(weapon.getPosition()).thenReturn(weaponPosition);
        when(weaponPosition.isAdjacent(playerPosition)).thenReturn(true);
        when(itemStore.findByRoomNumber(anyInt())).thenReturn(List.of(weapon));

        itemInteractionService.pickUpItem();

        verify(player).equipWeapon(weapon);
        verify(itemStore).remove(weapon);
        verify(dungeonRenderer).renderNotification(contains("Player picked up a weapon!"));
    }

    @Test
    public void testPickUpWeaponWhenPlayerHasWeapon() {
        Weapon currentWeapon = mock(Weapon.class);
        Weapon newWeapon = mock(Weapon.class);
        Position playerPosition = mock(Position.class);
        Position newWeaponPosition = mock(Position.class);

        when(player.getPosition()).thenReturn(playerPosition);
        when(newWeapon.getPosition()).thenReturn(newWeaponPosition);
        when(newWeaponPosition.isAdjacent(playerPosition)).thenReturn(true);
        when(player.getEquippedWeapon()).thenReturn(currentWeapon);
        when(player.unEquipWeapon()).thenReturn(currentWeapon);
        when(itemStore.findByRoomNumber(anyInt())).thenReturn(List.of(newWeapon));

        itemInteractionService.pickUpItem();

        verify(player).unEquipWeapon();
        verify(player).equipWeapon(newWeapon);
        verify(itemStore).add(currentWeapon);
        verify(itemStore).remove(newWeapon);
        verify(dungeonRenderer).renderNotification(contains("Player switched weapon!"));
    }

    @Test
    public void testPickUpConsumable() {
        Consumables consumable = mock(Consumables.class);
        Position playerPosition = mock(Position.class);
        Position consumablePosition = mock(Position.class);

        when(player.getPosition()).thenReturn(playerPosition);
        when(consumable.getPosition()).thenReturn(consumablePosition);
        when(consumablePosition.isAdjacent(playerPosition)).thenReturn(true);
        when(consumable.getHealthPoints()).thenReturn(20);
        when(itemStore.findByRoomNumber(anyInt())).thenReturn(List.of(consumable));

        itemInteractionService.pickUpItem();

        verify(player).heal(20);
        verify(itemStore).remove(consumable);
        verify(dungeonRenderer).renderNotification(contains("Player used a consumable!"));
    }

    @Test
    public void testPickUpNoItemNearby() {
        Position playerPosition = mock(Position.class);

        when(player.getPosition()).thenReturn(playerPosition);
        when(itemStore.findByRoomNumber(anyInt())).thenReturn(List.of());

        itemInteractionService.pickUpItem();

        verify(dungeonRenderer).renderNotification("No item to pick up");
    }
}