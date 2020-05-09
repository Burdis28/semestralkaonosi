package upce.sem.semestralkabe.controller;

import upce.sem.semestralkabe.dto.BidDtoOut;
import upce.sem.semestralkabe.dto.CreateBidDtoIn;
import upce.sem.semestralkabe.dto.CreateOfferDtoIn;
import upce.sem.semestralkabe.dto.CreateToyDtoIn;
import upce.sem.semestralkabe.dto.ToyDtoOut;
import upce.sem.semestralkabe.dto.UserNameDtoIn;
import upce.sem.semestralkabe.dto.LoginDtoIn;
import upce.sem.semestralkabe.dto.OfferDtoOut;
import upce.sem.semestralkabe.dto.RegisterDtoIn;
import java.util.List;
import javax.transaction.Transactional;
import upce.sem.semestralkabe.model.AppModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "toychange")
@Transactional
public class ToyTradingController {

  Logger logger = LoggerFactory.getLogger(ToyTradingController.class);

  @Autowired
  private AppModel appModel;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@RequestBody RegisterDtoIn dtoIn) {
    logger.info("Register is starting.");
    return appModel.register(dtoIn);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestBody LoginDtoIn dtoIn) {
    logger.info("Login is starting.");
    return appModel.login(dtoIn);
  }

  @RequestMapping(value = "/getAllOffers", method = RequestMethod.GET)
  public List<OfferDtoOut> getAllOffers() {
    logger.info("Listing all offers.");
    return appModel.getAllOffers();
  }

  @RequestMapping(value = "/getAllOffersOfUser", method = RequestMethod.POST)
  public List<OfferDtoOut> getAllOffersOfUser(@RequestBody UserNameDtoIn dtoIn) {
    logger.info("Listing all offers of user.");
    return appModel.getAllOffersOfUser(dtoIn, false);
  }

  @RequestMapping(value = "/getAllActiveOffersOfUser", method = RequestMethod.POST)
  public List<OfferDtoOut> getAllActiveOffersOfUser(@RequestBody UserNameDtoIn dtoIn) {
    logger.info("Listing all active offers of user.");
    return appModel.getAllOffersOfUser(dtoIn, true);
  }

  @RequestMapping(value = "/getAllBidsOfUser", method = RequestMethod.POST)
  public List<BidDtoOut> getAllBidsOfUser(@RequestBody UserNameDtoIn dtoIn) {
    logger.info("Listing all offers.");
    return appModel.getAllBidsOfUser(dtoIn, false);
  }

  @RequestMapping(value = "/getAllActiveBidsOfUser", method = RequestMethod.POST)
  public List<BidDtoOut> getAllActiveBidsOfUser(@RequestBody UserNameDtoIn dtoIn) {
    logger.info("Listing all offers.");
    return appModel.getAllBidsOfUser(dtoIn, true);
  }

  @RequestMapping(value = "/getAllActiveOffers", method = RequestMethod.GET)
  public List<OfferDtoOut> getAllActiveOffers() {
    logger.info("Listing all active offers.");
    return appModel.getAllActiveOffers();

  }

  @RequestMapping(value = "/getAllBids/{offerId}", method = RequestMethod.GET)
  public List<BidDtoOut> getAllBids(@PathVariable("offerId") Long offerId) {
    logger.info("Listing all bids for offerId: " + offerId);
    return appModel.getAllBids(offerId);
  }

  @RequestMapping(value = "/getOffer/{offerId}", method = RequestMethod.GET)
  public OfferDtoOut getOffer(@PathVariable("offerId") Long offerId) {
    logger.info("Getting offer based on offerId: " + offerId);
    return appModel.getOffer(offerId);
  }

  @RequestMapping(value = "/createOffer", method = RequestMethod.POST)
  public void createOffer(@RequestBody CreateOfferDtoIn dtoIn) {
    logger.info("Creating offer.");
    appModel.createOffer(dtoIn);
    logger.info("Offer was created.");
  }


  @RequestMapping(value = "/createBid", method = RequestMethod.POST)
  public void createBid(@RequestBody CreateBidDtoIn dtoIn) {
    logger.info("Creating bid.");
    appModel.createBid(dtoIn);
    logger.info("Bid was created.");
  }

  @RequestMapping(value = "/createToy", method = RequestMethod.POST)
  public void createToy(@RequestBody CreateToyDtoIn dtoIn) {
    logger.info("Creating toy.");
    appModel.createToy(dtoIn);
    logger.info("Toy was created.");
  }

  @RequestMapping(value = "/getAllToys", method = RequestMethod.POST)
  public List<ToyDtoOut> getAllToys(@RequestBody UserNameDtoIn dtoIn) {
    logger.info("Listing all toys.");
    return appModel.getAllToys(dtoIn);
  }

  @RequestMapping(value = "/getToy/{toyId}", method = RequestMethod.GET)
  public ToyDtoOut getToy(@PathVariable("toyId") Long toyId) {
    logger.info("Getting one specific toy.");
    return appModel.getToy(toyId);
  }

  @RequestMapping(value = "/acceptBid/{bidId}", method = RequestMethod.POST)
  public String acceptBid(@PathVariable("bidId") Long bidId) {
    logger.info("Accept Bid and close offer.");
    return appModel.acceptBid(bidId);
  }

  @RequestMapping(value = "/deleteOffer/{offerId}", method = RequestMethod.POST)
  public void deleteOffer(@PathVariable("offerId") Long offerId) {
    logger.info("Deleting offer.");
    appModel.deleteOffer(offerId);
    logger.info("Offer was deleted.");
  }

  @RequestMapping(value = "/deleteBid/{bidId}", method = RequestMethod.POST)
  public void deleteBid(@PathVariable("bidId") Long bidId) {
    logger.info("Deleting bid.");
    appModel.deleteBid(bidId);
    logger.info("Bid was deleted.");
  }

  @RequestMapping(value = "/deleteToy/{toyId}", method = RequestMethod.POST)
  public String deleteToy(@PathVariable("toyId") Long toyId) {
    logger.info("Deleting Toy.");
    return appModel.deleteToy(toyId);
  }

  @RequestMapping(value = "/initialize", method = RequestMethod.POST)
  public void initialize() {
    logger.info("Initialize.");
    appModel.initialize();
    logger.info("Successfully initialized.");
  }

}
