package mapper;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/10/7
 * Time: 5:35
 * Mail:star_1017@outlook.com
 */
import java.util.ArrayList;
import java.util.List;

import domain.user.User;

// This class follows a Unit of Work pattern and focuses on the changes
// of room by landlords, because a same account may be logged in by different users
// if they want to change the info of a same room, the data
// will be updated at same time

// Using in LandlordRoomServiceImp
public class UnitOfWork {

    private static UserMapper userMapper;
    static {
        userMapper = UserMapper.getInstance();
    }

    // Parameters
    @SuppressWarnings("rawtypes")
    private static ThreadLocal current = new ThreadLocal();
    private List<User> newObjects = new ArrayList<User>();
    private List<User> dirtyObjects = new ArrayList<User>();
    private List<User> deletedObjects = new ArrayList<User>();

    public static void newCurrent()
    {
        setCurrent(new UnitOfWork());
    }

    @SuppressWarnings("unchecked")
    public static void setCurrent(UnitOfWork uow)
    {
        current.set(uow);
    }

    public static UnitOfWork getCurrent()
    {
        return (UnitOfWork) current.get();
    }

    public void registerNew(User user)
    {
        Assert.notNull(Integer.valueOf(user.getId()), "id is null");
        Assert.isTrue(!dirtyObjects.contains(user), "object is dirty");
        Assert.isTrue(!deletedObjects.contains(user), "object is deleted");
        Assert.isTrue(!newObjects.contains(user), "object is new");
        newObjects.add(user);
    }

    public void registerDirty(User user)
    {
        Assert.notNull(Integer.valueOf(user.getId()), "id is null");
        Assert.isTrue(!deletedObjects.contains(user), "object is deleted");

        if (!dirtyObjects.contains(user) && !newObjects.contains(user))
        {
            dirtyObjects.add(user);
        }
    }

    public void registerDeleted(User user)
    {
        Assert.notNull(Integer.valueOf(user.getId()), "id is null");

        if (newObjects.remove(user))
            return;
        dirtyObjects.remove(user);

        if (!deletedObjects.contains(user))
        {
            deletedObjects.add(user);
        }
    }

    public void registerClean(User user)
    {
        Assert.notNull(user.getId(), "id is null");
    }

    public int commit() {
        int sum = 0;
        for (User user : dirtyObjects) {
            sum = sum + userMapper.updateUser(user);
        }
        for (User user : newObjects) {
            sum = sum + userMapper.addUser(user);
        }
        return sum;


    }



    // Assert class
    static class Assert{

        public static void notNull(Integer condition, String message)
        {
            if(condition == null)
            {
                System.out.println(message);
            }
        }

        public static void isTrue(boolean condition, String message)
        {
            if(!condition)
            {
                System.out.println(message);
            }

        }
    }
}
