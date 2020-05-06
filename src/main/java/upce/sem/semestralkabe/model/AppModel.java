package upce.sem.semestralkabe.model;

import upce.sem.semestralkabe.dao.BidDao;
import upce.sem.semestralkabe.dao.OfferDao;
import upce.sem.semestralkabe.dao.ToyDao;
import upce.sem.semestralkabe.dao.UserDao;
import upce.sem.semestralkabe.dto.BidDtoOut;
import upce.sem.semestralkabe.dto.CreateBidDtoIn;
import upce.sem.semestralkabe.dto.CreateOfferDtoIn;
import upce.sem.semestralkabe.dto.GetAllMyOffersDtoIn;
import upce.sem.semestralkabe.dto.LoginDtoIn;
import upce.sem.semestralkabe.dto.OfferDtoOut;
import upce.sem.semestralkabe.dto.RegisterDtoIn;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upce.sem.semestralkabe.schema.Bid;
import upce.sem.semestralkabe.schema.Offer;
import upce.sem.semestralkabe.schema.User;

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
      List<Bid> bids = bidDao.getAllBidsForOffer(offerId);
      List<BidDtoOut> dtoOutList = new ArrayList<>();

      for(Bid bid : bids) {
        dtoOutList.add(new BidDtoOut(bid.getOfferId(), bid.getUser().getName(), bid.getCaption(), bid.getDescription(), bid.getToys()));
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all bids: " + e);
      throw e;
    }
  }

  public String login(LoginDtoIn dtoIn) {
    //return "Nemam rad upce";
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user != null) {
        if (user.getPassword().equals(dtoIn.getPassword())) {
          return "" + user.getId();
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
        user.addOffer(offer);
        offerDao.save(offer);
        userDao.save(user);
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
        user.addBid(bid);
        bidDao.save(bid);
        userDao.save(user);
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
        offer.ifPresent(offer1 -> offer1.setWinner(bid.get().getOfferId()));
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
    Offer offer4 = new Offer("Caption 4", "Description 4", null, admin);
    Offer offer5 = new Offer("Caption 5", "Description 5", null, admin);

    admin.addOffer(offer1);
    offerDao.save(offer1);
    userDao.save(admin);

    admin.addOffer(offer2);
    offerDao.save(offer2);
    userDao.save(admin);

    admin.addOffer(offer3);
    offerDao.save(offer3);
    userDao.save(admin);

    admin.addOffer(offer4);
    offerDao.save(offer4);
    userDao.save(admin);

    admin.addOffer(offer5);
    offerDao.save(offer5);
    userDao.save(admin);

    Bid bid = new Bid(offerDao.getAll().get(0).getId(), "caption bid", "description bid", null, admin2);
    Bid bid2 = new Bid(offerDao.getAll().get(1).getId(), "caption bid 2", "description bid 2", null, admin2);
    Bid bid3 = new Bid(offerDao.getAll().get(0).getId(), "caption bid 3", "description bid 3", null, admin2);

    bidDao.save(bid);
    userDao.save(admin2);

    bidDao.save(bid2);
    userDao.save(admin2);

    bidDao.save(bid3);
    userDao.save(admin2);


  }

  public List<OfferDtoOut> getAllActiveOffers() {
    try {
      List<Offer> offers = offerDao.getAllActiveOffers();
      List<OfferDtoOut> dtoOutList = new ArrayList<>();

      for (Offer offer : offers) {
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), offer.getToys()));
      }

      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all active offers: " + e);
      throw e;
    }
  }

  public void deleteOffer(Long offerId) {
    if(offerId != null) {
      offerDao.delete(offerDao.get(offerId).get());
    }
  }

  public List<OfferDtoOut> getAllOffersOfUser(GetAllMyOffersDtoIn dtoIn, boolean active) {
    try {
      List<Offer> offers = offerDao.getAllOffersOfUser(dtoIn.getUserId(), active);
      List<OfferDtoOut> dtoOutList = new ArrayList<>();

      for (Offer offer : offers) {
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), offer.getToys()));
      }

      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all active offers of User: " + e);
      throw e;
    }
  }

  public List<BidDtoOut> getAllBidsOfUser(GetAllMyOffersDtoIn dtoIn, boolean active) {
    try {
      List<Bid> bids = bidDao.getAllBidsOfUser(dtoIn.getUserId(), active);
      List<BidDtoOut> dtoOutList = new ArrayList<>();

      for(Bid bid : bids) {
        dtoOutList.add(new BidDtoOut(bid.getOfferId(), bid.getUser().getName(), bid.getCaption(), bid.getDescription(), bid.getToys()));
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all bids: " + e);
      throw e;
    }
  }
}
