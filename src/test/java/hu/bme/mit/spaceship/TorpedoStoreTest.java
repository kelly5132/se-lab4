package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    // Note: the explicit failure rate (0.0 / 1.0) makes the tests deterministic and
    // independent of the IVT_RATE environment variable.

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1, 0.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void constructor_SetsTorpedoCount() {
        // Arrange + Act
        TorpedoStore store = new TorpedoStore(5, 0.0);

        // Assert
        assertEquals(5, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void isEmpty_NoTorpedoes_True() {
        // Arrange + Act
        TorpedoStore store = new TorpedoStore(0, 0.0);

        // Assert
        assertTrue(store.isEmpty());
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_Success_DecreasesTorpedoCount() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, 0.0);

        // Act
        boolean result = store.fire(2);

        // Assert
        assertTrue(result);
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedoes_StoreBecomesEmpty() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, 0.0);

        // Act
        boolean result = store.fire(2);

        // Assert
        assertTrue(result);
        assertTrue(store.isEmpty());
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_FailureRateOne_FailsAndKeepsTorpedoes() {
        // Arrange
        TorpedoStore store = new TorpedoStore(3, 1.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertFalse(result);
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void fire_Zero_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(3, 0.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(3, 0.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(4));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void fire_EmptyStore_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(0, 0.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
    }
}
