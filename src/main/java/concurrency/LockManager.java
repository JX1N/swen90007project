package concurrency;

import java.util.HashMap;
import java.util.Map;

/**
 * Lock manager class that manages locks for different objects
 */
public class LockManager {

    private final Map<Object, ReadWriteLock> lockMap;

    private static LockManager manager;

    /**
     * Private constructor to prevent instantiating objects
     */
    private LockManager() {
        lockMap = new HashMap<>();
    }

    /**
     * Singleton method to get an instance of the LockManager
     * @return instance of the LockManager
     */
    public static LockManager getInstance() {
        if (LockManager.manager == null) {
            LockManager.manager = new LockManager();
        }
        return LockManager.manager;
    }

    /**
     * Acquires a read lock on an object
     * @param toLock the object to be locked
     */
    public synchronized void acquireReadLock(Object toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockRead();
    }

    /**
     * Acquires a write lock on an object
     * @param toLock the object to be locked
     */
    public synchronized void acquireWriteLock(Object toLock)
            throws InterruptedException {
        getReadWriteLock(toLock).lockWrite();
    }

    /**
     * Releases a read lock on an object
     * @param toLock the object to be released
     */
    public synchronized void releaseReadLock(Object toLock) {
        getReadWriteLock(toLock).unlockRead();
    }

    /**
     * Releases a write lock on an object
     * @param toLock the object to be released
     */
    public synchronized void releaseWriteLock(Object toLock) {
        getReadWriteLock(toLock).unlockWrite();
    }

    /**
     * Releases all locks on an object
     * @param toLock the object to be released
     */
    public synchronized void releaseAllLocksOn(Object toLock) {
        getReadWriteLock(toLock).unlock();
    }

    /**
     * Releases all locks on all objects
     */
    public synchronized void releaseAllLocks() {
        for (Map.Entry<Object, ReadWriteLock> entry : lockMap.entrySet()) {
            entry.getValue().unlock();
        }
    }

    /**
     * Get a read write lock on an object. Create one if the lock does not exist
     * @param toLock the object of the lock
     * @return the read write lock on the object
     */
    private ReadWriteLock getReadWriteLock(Object toLock) {
        ReadWriteLock lock = lockMap.get(toLock);
        if (lock == null) {
            lockMap.putIfAbsent(toLock, new ReadWriteLock());
            lock = lockMap.get(toLock);
        }
        return lock;
    }
}
