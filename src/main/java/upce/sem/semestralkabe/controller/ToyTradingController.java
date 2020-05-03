package upce.sem.semestralkabe.controller;

import upce.sem.semestralkabe.dto.BidDtoOut;
import upce.sem.semestralkabe.dto.CreateBidDtoIn;
import upce.sem.semestralkabe.dto.CreateOfferDtoIn;
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
  public void register(@RequestBody RegisterDtoIn dtoIn) {
    logger.info("Register is starting.");
    appModel.register(dtoIn);
    logger.info("Register has ended.");
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

  @RequestMapping(value = "/getAllActiveOffers", method = RequestMethod.GET)
  public List<OfferDtoOut> getAllActiveOffers() {
    return appModel.getAllActiveOffers();
  }

  @RequestMapping(value = "/getAllBids/{offerId}", method = RequestMethod.GET)
  public List<BidDtoOut> getAllBids(@PathVariable("offerId") Long offerId) {
    logger.info("Listing all bids for offerId: " + offerId);
    return appModel.getAllBids(offerId);
  }

  @RequestMapping(value = "/createOffer", method = RequestMethod.POST)
  public void createOffer(@RequestBody CreateOfferDtoIn dtoIn) {
    logger.info("Creating offer.");
    appModel.createOffer(dtoIn);
    logger.info("Offer was created.");
  }

  @RequestMapping(value = "/createBid", method = RequestMethod.POST)
  public void createOffer(@RequestBody CreateBidDtoIn dtoIn) {
    logger.info("Creating bid.");
    appModel.createBid(dtoIn);
    logger.info("Bid was created.");
  }

  @RequestMapping(value = "/acceptBid/{bidId}", method = RequestMethod.POST)
  public void acceptBid(@PathVariable("bidId") Long bidId) {
    logger.info("Accept Bid and close offer.");
    appModel.acceptBid(bidId);
    logger.info("Bid was accepted and offer closed.");
  }

  @RequestMapping(value = "/deleteOffer/{offerId}", method = RequestMethod.POST)
  public void createOffer(@PathVariable("offerId") Long offerId) {
    logger.info("Deleting offer.");
    appModel.deleteOffer(offerId);
    logger.info("Offer was deleted.");
  }

  @RequestMapping(value = "/initialize", method = RequestMethod.POST)
  public void initialize() {
    logger.info("Initialize.");
    appModel.initialize();
    logger.info("Successfully initialized.");
  }

}
