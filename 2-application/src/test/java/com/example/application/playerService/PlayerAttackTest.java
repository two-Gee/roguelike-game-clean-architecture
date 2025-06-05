package com.example.application.playerService;

import com.example.application.DungeonRenderer;
import com.example.application.stores.MonsterStore;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.monster.Monster;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerAttackTest {

    @Mock
    private Player mockPlayer;
    @Mock
    private MonsterStore mockMonsterStore;
    @Mock
    private DungeonRenderer mockDungeonRenderer;
    @Mock
    private Monster mockMonster1;
    @Mock
    private Monster mockMonster2;

    private PlayerAttack playerAttack;

    @Before
    public void setUp() {
        playerAttack = new PlayerAttack(mockPlayer, mockMonsterStore, mockDungeonRenderer);
        when(mockPlayer.getRoomNumber()).thenReturn(1);
    }

    @Test
    public void testAttackMonster_monsterExistsAndIsNotDead() {
        Position targetPosition = new Position(1, 1);
        when(mockMonster1.getPosition()).thenReturn(targetPosition);
        when(mockMonster1.isDead()).thenReturn(false);
        when(mockMonster1.getName()).thenReturn("Orc");
        when(mockPlayer.getName()).thenReturn("Player");
        when(mockPlayer.getAttack()).thenReturn(10);
        when(mockMonsterStore.findByRoomNumber(anyInt())).thenReturn(Collections.singletonList(mockMonster1));

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer).attack(mockMonster1);
        ArgumentCaptor<String> notificationCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockDungeonRenderer).renderNotification(notificationCaptor.capture());
        assertEquals("Player attacks Orc! Orc took 10 damage.", notificationCaptor.getValue());
        verify(mockMonsterStore, never()).remove(any(UUID.class));
    }

    @Test
    public void testAttackMonster_monsterExistsAndIsDeadAfterAttack() {
        Position targetPosition = new Position(1, 1);
        UUID monsterId = UUID.randomUUID();
        when(mockMonster1.getPosition()).thenReturn(targetPosition);
        when(mockMonster1.isDead()).thenReturn(true);
        when(mockMonster1.getName()).thenReturn("Orc");
        when(mockMonster1.getId()).thenReturn(monsterId);
        when(mockPlayer.getName()).thenReturn("Player");
        when(mockPlayer.getAttack()).thenReturn(20);
        when(mockMonsterStore.findByRoomNumber(anyInt())).thenReturn(Collections.singletonList(mockMonster1));

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer).attack(mockMonster1);
        ArgumentCaptor<String> attackNotificationCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockDungeonRenderer, times(2)).renderNotification(attackNotificationCaptor.capture());
        List<String> capturedNotifications = attackNotificationCaptor.getAllValues();
        assertEquals("Player attacks Orc! Orc took 20 damage.", capturedNotifications.get(0));
        assertEquals("Player killed Orc!", capturedNotifications.get(1));
        verify(mockMonsterStore).remove(monsterId);
    }

    @Test
    public void testAttackMonster_multipleMonstersOneAttacked() {
        Position targetPosition = new Position(2, 2);
        when(mockMonster1.getPosition()).thenReturn(new Position(1, 1));
        when(mockMonster2.getPosition()).thenReturn(targetPosition);
        when(mockMonster2.isDead()).thenReturn(false);
        when(mockMonster2.getName()).thenReturn("Orc");
        when(mockPlayer.getName()).thenReturn("Player");
        when(mockPlayer.getAttack()).thenReturn(50);
        when(mockMonsterStore.findByRoomNumber(anyInt())).thenReturn(Arrays.asList(mockMonster1, mockMonster2));

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer, never()).attack(mockMonster1);
        verify(mockPlayer).attack(mockMonster2);
        ArgumentCaptor<String> notificationCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockDungeonRenderer).renderNotification(notificationCaptor.capture());
        assertEquals("Player attacks Orc! Orc took 50 damage.", notificationCaptor.getValue());
        verify(mockMonsterStore, never()).remove(any(UUID.class));
    }

    @Test
    public void testAttackMonster_noMonsterAtPosition() {
        Position targetPosition = new Position(3, 3);
        when(mockMonster1.getPosition()).thenReturn(new Position(1, 1));
        when(mockMonsterStore.findByRoomNumber(anyInt())).thenReturn(Collections.singletonList(mockMonster1));

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer, never()).attack(any(Monster.class));
        verify(mockDungeonRenderer, never()).renderNotification(anyString());
        verify(mockMonsterStore, never()).remove(any(UUID.class));
    }

    @Test
    public void testAttackMonster_noMonstersInRoom() {
        Position targetPosition = new Position(1, 1);
        when(mockMonsterStore.findByRoomNumber(anyInt())).thenReturn(Collections.emptyList());

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer, never()).attack(any(Monster.class));
        verify(mockDungeonRenderer, never()).renderNotification(anyString());
        verify(mockMonsterStore, never()).remove(any(UUID.class));
    }

    @Test
    public void testAttackMonster_playerGetsCorrectRoomNumber() {
        Position targetPosition = new Position(1, 1);
        when(mockMonster1.getPosition()).thenReturn(targetPosition);
        when(mockMonster1.isDead()).thenReturn(false);
        when(mockMonsterStore.findByRoomNumber(1)).thenReturn(Collections.singletonList(mockMonster1));

        playerAttack.attackMonster(targetPosition);

        verify(mockPlayer).getRoomNumber();
        verify(mockMonsterStore).findByRoomNumber(1);
    }
}