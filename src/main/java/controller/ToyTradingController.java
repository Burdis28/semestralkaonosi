package controller;

import dto.BidDtoOut;
import dto.CreateBidDtoIn;
import dto.CreateOfferDtoIn;
import dto.LoginDtoIn;
import dto.OfferDtoOut;
import dto.RegisterDtoIn;
import java.util.List;
import javax.transaction.Transactional;
import model.AppModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/toychange")
@Transactional
public class ToyTradingController {

  Logger logger = LoggerFactory.getLogger(ToyTradingController.class);

  @Autowired
  private AppModel appModel;


  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public void register(@RequestBody RegisterDtoIn dtoIn) {
    appModel.register(dtoIn);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestBody LoginDtoIn dtoIn) {
    return appModel.login(dtoIn);
  }

  @RequestMapping(value = "/getAllOffers", method = RequestMethod.GET)
  public List<OfferDtoOut> getAllOffers() {
    return appModel.getAllOffers();
  }

  @RequestMapping(value = "/getAllBids/{offerId}", method = RequestMethod.GET)
  public List<BidDtoOut> getAllBids(@PathVariable("offerId") Long offerId) {
    return appModel.getAllBids(offerId);
  }

  @RequestMapping(value = "/createOffer", method = RequestMethod.POST)
  public void createOffer(@RequestBody CreateOfferDtoIn dtoIn) {
    appModel.createOffer(dtoIn);
  }

  @RequestMapping(value = "/createBid", method = RequestMethod.POST)
  public void createOffer(@RequestBody CreateBidDtoIn dtoIn) {
    appModel.createBid(dtoIn);
  }
}
