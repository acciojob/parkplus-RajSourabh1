package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
       ParkingLot parkingLot = new ParkingLot();
       parkingLot.setName(name);
       parkingLot.setAddress(address);
       parkingLotRepository1.save(parkingLot);
       return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spots = parkingLot.getSpotList();

        Spot spot = new Spot();
        if(numberOfWheels>2 && numberOfWheels<=4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        } else if (numberOfWheels>4) {
            spot.setSpotType(SpotType.OTHERS);
        }
        else
            spot.setSpotType(SpotType.TWO_WHEELER);

        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setParkingLot(parkingLot);
        spots.add(spot);

        parkingLot.setSpotList(spots);
        parkingLotRepository1.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
//       Spot spot = spotRepository1.findById(spotId).get();
//       ParkingLot parkingLot = spot.getParkingLot();
//        List<Spot> spots = parkingLot.getSpotList();
//
//        if(spots.contains(spot))
//            spots.remove(spot);
//        for(Spot spot1: spots){
//            if (Objects.equals(spot1, spot)){
//                spots.remove(spot);
//            }
//            break;
//        }
//        parkingLot.setSpotList(spots);
//        spotRepository1.deleteById(spotId);
//        parkingLotRepository1.save(parkingLot);
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

            ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

            Spot spot = null;
            List<Spot> spots = parkingLot.getSpotList();

            for (Spot spot1 : spots) {
                if (spot1.getId() == spotId)
                    spot = spot1;
            }

            spot.setParkingLot(parkingLot);
            spot.setPricePerHour(pricePerHour);
            parkingLot.setSpotList(spots);
          //  parkingLot.getSpotList()
            parkingLotRepository1.save(parkingLot);
            return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
//        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
//        List<Spot> spots = parkingLot.getSpotList();
//        for(Spot spot:spots){
//            spotRepository1.delete(spot);
//        }

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
