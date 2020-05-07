package upce.sem.semestralkabe.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import upce.sem.semestralkabe.dao.BidDao;
import upce.sem.semestralkabe.dao.OfferDao;
import upce.sem.semestralkabe.dao.ToyDao;
import upce.sem.semestralkabe.dao.UserDao;
import upce.sem.semestralkabe.dto.BidDtoOut;
import upce.sem.semestralkabe.dto.CreateBidDtoIn;
import upce.sem.semestralkabe.dto.CreateOfferDtoIn;
import upce.sem.semestralkabe.dto.CreateToyDtoIn;
import upce.sem.semestralkabe.dto.ToyDtoOut;
import upce.sem.semestralkabe.dto.UserNameDtoIn;
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
import upce.sem.semestralkabe.schema.Toy;
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


  public String register(RegisterDtoIn dtoIn) {
    try {
      if (dtoIn != null) {
        User user = new User();
        user.setBiddedOffers(null);
        user.setOffers(null);
        user.setPassword(dtoIn.getPassword());
        user.setUsername(dtoIn.getUsername());
        user.setName(dtoIn.getName());
        userDao.save(user);
        return "1";
      }
    } catch (Exception e) {
      logger.error("Error during creating user: " + e);
      return "-1";
    }
    return "-1";
  }

  public List<OfferDtoOut> getAllOffers() {
    try {
      List<Offer> offers = offerDao.getAll();
      List<OfferDtoOut> dtoOutList = new ArrayList<>();

      for (Offer offer : offers) {
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), createListOfToysDtoOut(offer.getToys())));
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

      for (Bid bid : bids) {
        dtoOutList.add(new BidDtoOut(bid.getId(), bid.getOfferId(), bid.getUser().getName(), bid.getCaption(), bid.getDescription(),createListOfToysDtoOut(bid.getToys())));
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all bids: " + e);
      throw e;
    }
  }

  public String login(LoginDtoIn dtoIn) {
    try {
      if (dtoIn != null) {
        User user = userDao.getByUsername(dtoIn.getUsername());
        if (user != null) {
          if (user.getPassword().equals(dtoIn.getPassword())) {
            return "" + user.getUsername();
          } else {
            return "-1";
          }
        } else {
          return "-1";
        }
      } else {
        return "-1";
      }
    } catch (Exception e) {
      logger.error("User doesnt exist: " + e);
      return "-1";
    }
  }

  public void createOffer(CreateOfferDtoIn dtoIn) {
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user != null) {
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
      if (user != null) {
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
    if (bidId != null) {
      Optional<Bid> bid = bidDao.get(bidId);
      if (bid.isPresent()) {
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

    Toy toy1 = new Toy("toy1", admin);
    Toy toy2 = new Toy("toy2", admin);
    Toy toy3 = new Toy("toy3", admin);
    Toy toy4 = new Toy("toy4", admin);
    Toy toy5 = new Toy("toy5", admin);
    Toy toy6 = new Toy("toy6", admin2);
    Toy toy7 = new Toy("toy7", admin2);
    Toy toy8 = new Toy("toy8", admin2);

    offer1.addToy(toy1);
    offer2.addToy(toy2);
    offer3.addToy(toy3);
    offer4.addToy(toy4);
    offer5.addToy(toy5);

    toyDao.save(toy1);
    toyDao.save(toy2);
    toyDao.save(toy3);
    toyDao.save(toy4);
    toyDao.save(toy5);

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

    bid.addToy(toy6);
    bid2.addToy(toy7);
    bid3.addToy(toy8);

    toyDao.save(toy6);
    toyDao.save(toy7);
    toyDao.save(toy8);

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
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), createListOfToysDtoOut(offer.getToys())));
      }

      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all active offers: " + e);
      throw e;
    }
  }

  public void deleteOffer(Long offerId) {
    if (offerId != null) {
      offerDao.delete(offerDao.get(offerId).get());
    }
  }

  public List<OfferDtoOut> getAllOffersOfUser(UserNameDtoIn dtoIn, boolean active) {
    try {
      List<Offer> offers = offerDao.getAllOffersOfUser(dtoIn.getUsername(), active);
      List<OfferDtoOut> dtoOutList = new ArrayList<>();

      for (Offer offer : offers) {
        dtoOutList.add(new OfferDtoOut(offer.getId(), offer.getUser().getName(), offer.getCaption(), offer.getDescription(), createListOfToysDtoOut(offer.getToys())));
      }

      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all active offers of User: " + e);
      throw e;
    }
  }

  public List<BidDtoOut> getAllBidsOfUser(UserNameDtoIn dtoIn, boolean active) {
    try {
      List<Bid> bids = bidDao.getAllBidsOfUser(dtoIn.getUsername(), active);
      List<BidDtoOut> dtoOutList = new ArrayList<>();

      for (Bid bid : bids) {
        dtoOutList.add(new BidDtoOut(bid.getId(), bid.getOfferId(), bid.getUser().getName(), bid.getCaption(), bid.getDescription(), createListOfToysDtoOut(bid.getToys())));
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all bids: " + e);
      throw e;
    }
  }

  private List<ToyDtoOut> createListOfToysDtoOut(List<Toy> toys) {
    List<ToyDtoOut> dtoOuts = new ArrayList<>();
    for (Toy toy : toys) {
      ToyDtoOut dtoOut = new ToyDtoOut();
      dtoOut.setName(toy.getName());
      dtoOut.setImageData(imgToBase64String(toy.getImage(), ".jpg"));
    }
    return dtoOuts;

  }

  public void createToy(CreateToyDtoIn dtoIn) {
    if (dtoIn != null) {
      User user = userDao.getByUsername(dtoIn.getUsername());
      if (user != null) {
        Toy toy = new Toy();
        toy.setName(dtoIn.getName());
        toy.setUser(user);
        toy.setImage(createImage(dtoIn.getImageData()));
        user.addToy(toy);
        toyDao.save(toy);
        userDao.save(user);
      } else {
        logger.error("User doesnt exist.");
      }
    } else {
      logger.error("DtoIn is null.");
    }
  }

  private BufferedImage createImage(String imageData) {
    BufferedImage img = null;
    if(imageData != null) {
      String base64Image = imageData.split(",")[1];
      byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
      try {
        img = ImageIO.read(new ByteArrayInputStream(imageBytes));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return img;
    } else {
      logger.error("There are no imageData to build Image.");
      return null;
    }
  }

  public static String imgToBase64String(BufferedImage img, String formatName)
  {
    final ByteArrayOutputStream os = new ByteArrayOutputStream();

    try
    {
      ImageIO.write(img, formatName, os);
      return Base64.getEncoder().encodeToString(os.toByteArray());
    }
    catch (final IOException ioe)
    {
      throw new UncheckedIOException(ioe);
    }
  }

  public static BufferedImage base64StringToImg(String base64String) {
    try {
      return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
    } catch (final IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }

  public List<ToyDtoOut> getAllToys(UserNameDtoIn dtoIn) {
    try {
      List<Toy> toys = toyDao.getAllToysOfUser(dtoIn.getUsername());
      List<ToyDtoOut> dtoOutList = new ArrayList<>();

      for (Toy toy : toys) {
        ToyDtoOut toyDtoOut = new ToyDtoOut();
        toyDtoOut.setName(toy.getName());
        dtoOutList.add(toyDtoOut);
      }
      return dtoOutList;
    } catch (Exception e) {
      logger.error("Error during getting all bids: " + e);
      throw e;
    }
  }
}
