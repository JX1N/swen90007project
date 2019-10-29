package mapper;

import domain.Degree;
import domain.Subject;
import domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The registry class which implements the identity class
 */
public class Registry {
    private static HashMap<Integer, Subject> subjectMap = new HashMap<>();
    private static HashMap<Integer, Degree> degreeMap = new HashMap<>();
    private static HashMap<String, User> userMap = new HashMap<>();

    /**
     * Get a subject object
     * @param id the id of the subject
     * @return a subject object
     */
    public static Subject loadSubject(int id) {
        return subjectMap.get(id);
    }

    /**
     * Get all subjects in the identity map
     * @return a list of subject objects
     */
    public static List<Subject> loadAllSubjects() {
        return new ArrayList<Subject>(subjectMap.values());
    }

    /**
     * Save a subject in the identity map
     * @param subject the subject to be saved
     * @return successful or not
     */
    public static boolean saveSubject(Subject subject) {
        subjectMap.put(subject.getId(), subject);
        return true;
    }

    /**
     * Delete a subject from the identity map
     * @param id the subject to be deleted
     * @return successful or not
     */
    public static boolean deleteSubject(int id) {
        subjectMap.remove(id);
        return true;
    }

    /**
     * Decide if there's any subject in the identity map
     * @return if there's any subject in the identity map
     */
    public static boolean subjectEmpty() {
        return subjectMap.isEmpty();
    }

    /**
     * Get a degree from the identity map
     * @param id the id of the degree
     * @return a degree object
     */
    public static Degree loadDegree(int id) {
        return degreeMap.get(id);
    }

    /**
     * Get all degrees from the identity map
     * @return a list of degrees
     */
    public static List<Degree> loadAllDegrees() {
        return new ArrayList<Degree>(degreeMap.values());
    }

    /**
     * Save a degree in the identity map
     * @param degree the degree object to be saved
     * @return successful or not
     */
    public static boolean saveDegree(Degree degree) {
        degreeMap.put(degree.getId(), degree);
        return true;
    }

    /**
     * Delete a degree from the identity map
     * @param id the id of the degree
     * @return successful or not
     */
    public static boolean deleteDegree(int id) {
        degreeMap.remove(id);
        return true;
    }

    /**
     * If there's any degree in the identity map
     * @return if there's any degree in the identity map
     */
    public static boolean degreeEmpty() {
        return degreeMap.isEmpty();
    }

    /**
     * Get a user from the identity map
     * @param username the username of the user
     * @return a user object
     */
    public static User loadUser(String username) {
        return userMap.get(username);
    }

    /**
     * Get all users from the identity map
     * @return a list of users
     */
    public static List<User> loadAllUsers() {
        return new ArrayList<User>(userMap.values());
    }

    /**
     * Save a user in the identity map
     * @param user a user object to be saved
     * @return successful or not
     */
    public static boolean saveUser(User user) {
        userMap.put(user.getUsername(), user);
        return true;
    }

    /**
     * Delete a user from the identity map
     * @param username the username of the user
     * @return successful or not
     */
    public static boolean deleteUser(String username) {
        userMap.remove(username);
        return true;
    }

    /**
     * Decide if there are any users in the identity map
     * @return if there are any users in the identity map
     */
    public static boolean userEmpty() {
        return userMap.isEmpty();
    }

}
