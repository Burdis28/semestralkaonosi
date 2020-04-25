package model;

import dao.BidDao;
import dao.OfferDao;
import dao.ToyDao;
import dao.UserDao;
import dto.BidDtoOut;
import dto.CreateBidDtoIn;
import dto.CreateOfferDtoIn;
import dto.LoginDtoIn;
import dto.OfferDtoOut;
import dto.RegisterDtoIn;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import schema.Bid;
import schema.Offer;
import schema.User;

@Component
@Service
public class AppModel {

  Logger logger = LoggerFactory.getLogger(AppModel.class);

  @Autowired
  private UserDao userDao;
  @Autowired
  private ToyDao toyDao;
  @Autowired
  private OfferDao offerDao;
  @Autowired
  private BidDao bidDao;


  public void register(RegisterDtoIn dtoIn) {
    try {
      if (dtoIn != null) {
        User user = new User();
        user.setBiddedOffers(null);
        user.setOffers(null);
        user.setPassword(dtoIn.getPassword());
        user.setUsername(dtoIn.getUsername());
        user.setName(dtoIn.getName());
        userDao.save(user);
      }
    } catch (Exception e) {
      logger.error("Error during saving user: " + e);
      throw e;
    }
  }

  public List<OfferDtoOut> getAllOffers() {
    try {
      List<Offer> offers = offerDao.getAll();
      List<OfferDtoOut> dtoOutList = new ArrayList<>();

      for (Offer offer : offers) {
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), offer.getToys()));
      }

      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all offers: " + e);
      throw e;
    }
  }

  public List<BidDtoOut> getAllBids(Long offerId) {
    try {
      List<Bid> bids = bidDao.getAll();
      List<BidDtoOut> dtoOutList = new ArrayList<>();

      for(Bid bid : bids) {
        if(bid.getOfferId().equals(offerId)) {
          dtoOutList.add(new BidDtoOut(bid.getOfferId(), bid.getUser().getName(), bid.getCaption(), bid.getDescription(), bid.getToys()));
        }
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during saving user: " + e);
      throw e;
    }
  }

  public String login(LoginDtoIn dtoIn) {
    //return "Nemam rad upce";
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user != null) {
        if (user.getPassword().equals(dtoIn.getPassword())) {
          return "1";
        } else {
          return "0";
        }
      } else {
        return "0";
      }
    } else {
      return "0";
    }
  }

  public void createOffer(CreateOfferDtoIn dtoIn) {
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user!= null) {
        Offer offer = new Offer(dtoIn.getCaption(), dtoIn.getDescription(), dtoIn.getToys(), user);
        offerDao.save(offer);
      } else {
        logger.error("User doesnt exist.");
      }
    } else {
      logger.error("DtoIn is null.");
    }
  }

  public void createBid(CreateBidDtoIn dtoIn) {
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user!= null) {
        Bid bid = new Bid(dtoIn.getOfferId(), dtoIn.getCaption(), dtoIn.getDescription(), dtoIn.getToys(), user);
        bidDao.save(bid);
      } else {
        logger.error("User doesnt exist.");
      }
    } else {
      logger.error("DtoIn is null.");
    }
  }

  public void acceptBid(Long bidId) {
    //accept bid and close offer
    if(bidId != null) {
      Optional<Bid> bid = bidDao.get(bidId);
      if(bid.isPresent()) {
        bid.get().setActive(false);
        Optional<Offer> offer = offerDao.get(bid.get().getOfferId());
        offer.ifPresent(offer1 -> offer1.setActive(false));
        offer.ifPresent(offer1 -> offer1.setWinner(bid.get()));
        List<Bid> bids = bidDao.getAllBidsForOffer(bid.get().getOfferId());
        for (Bid bid1 : bids) {
          bid1.setActive(false);
          bidDao.save(bid1);
        }
      }
    }
  }

  public void initialize() {
    User admin = new User("admin", "password123", "Administrator", "745862458", null, null);
    User admin2 = new User("admin2", "password123", "Administrator 2", "745862447", null, null);
    userDao.save(admin);
    userDao.save(admin2);
    Offer offer1 = new Offer("Caption 1", "Description 1", null, admin);
    Offer offer2 = new Offer("Caption 2", "Description 2", null, admin);
    Offer offer3 = new Offer("Caption 3", "Description 3", null, admin);
    Offer offer4 = new Offer("Caption 4", "Description 4", null, admin2);
    Offer offer5 = new Offer("Caption 5", "Description 5", null, admin);
    offerDao.save(offer1); offerDao.save(offer2); offerDao.save(offer3); offerDao.save(offer4); offerDao.save(offer5);
    Bid bid = new Bid(offerDao.getAll().get(0).getId(), "caption bid", "description bid", null, admin2);
    bidDao.save(bid);

  }

  public List<OfferDtoOut> getAllActiveOffers() {
    return null;
  }

  public void deleteOffer(Long offerId) {
    if(offerId != null) {
      offerDao.delete(offerDao.get(offerId).get());
    }
  }
}
