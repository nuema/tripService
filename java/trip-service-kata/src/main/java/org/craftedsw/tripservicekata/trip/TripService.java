package org.craftedsw.tripservicekata.trip;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import static java.util.Collections.emptyList;

public class TripService {

    private Supplier<User> getLoggedUser;
    private Function<User, List<Trip>> findTripsByUser;

    public TripService() {
        getLoggedUser = UserSession.getInstance()::getLoggedUser;
        findTripsByUser = TripDAO::findTripsByUser;
    }

    public TripService(Supplier<User> getLoggedUser) {
        this.getLoggedUser = getLoggedUser;
    }

    public TripService setFindTripsByUser(Function<User, List<Trip>> findTripsByUser) {
        this.findTripsByUser = findTripsByUser;
        return this;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        return areFriends(user, getLoggedUser()) ?
                findTripsByUser.apply(user) : emptyList();
    }

    private User getLoggedUser() {
        User loggedUser = getLoggedUser.get();
        if (loggedUser == null)
            throw new UserNotLoggedInException();

        return loggedUser;
    }

    private boolean areFriends(User user, User otherUser) {
        return user.getFriends().contains(otherUser);
    }
}
