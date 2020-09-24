package org.craftedsw.tripservicekata.trip;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.craftedsw.tripservicekata.exception.*;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class TripServiceTest {

    TripService tripService;

    @Test
    public void shouldFailWhenUserIsNotLogged() {
        tripService = new TripService(() -> null);

        Assertions.assertThrows(UserNotLoggedInException.class,
                () -> tripService.getTripsByUser(new User()));
    }

    @Test
    void shouldReturnNoTrips() {
        tripService = new TripService(User::new);
        User notAFriend = new User();

        Assertions.assertTrue(tripService.getTripsByUser(notAFriend).isEmpty());
    }

    @Test
    void shouldReturnTrips() {

        //Given
        User loggedUser = new User();
        User aFriend = new User();
        aFriend.addFriend(loggedUser);

        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip());

        tripService = new TripService(() -> loggedUser)
                .setFindTripsByUser((user) -> trips);

        //When
        Assertions.assertTrue(!tripService.getTripsByUser(aFriend).isEmpty());
    }
}
