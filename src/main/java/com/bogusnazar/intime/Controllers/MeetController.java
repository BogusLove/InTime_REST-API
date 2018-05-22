package com.bogusnazar.intime.Controllers;

import com.bogusnazar.intime.DAO.MeetRepository;
import com.bogusnazar.intime.DAO.UserRepository;
import com.bogusnazar.intime.Models.Meet;
import com.bogusnazar.intime.Models.MeetDataDto;
import com.bogusnazar.intime.Models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MeetController {

    @Autowired
    private MeetRepository meetRepository;
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(MeetController.class);

    @PostConstruct
    public void init() {
        if (meetRepository != null)
            logger.info("Class configured");
        else
            logger.info("Class isn't configured");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = {"application/json"})
    public String createMeet() {
        Meet meet = new Meet();
        meet.setName("Meet");
        meet.setCreatorID("5afee9e2c3eb8f0578401f07");
        Meet meet1 = new Meet();
        meet1.setName("Meet2");
        meet1.setCreatorID("5afee9e2c3eb8f0578401f07");
        Meet meet2 = new Meet();
        meet2.setName("Meet3");
        meetRepository.save(meet);
        meetRepository.save(meet1);
        meetRepository.save(meet2);
        User user = new User();
        user.setFirstName("Artem");
        user.setLastName("Bogus");
        user.setEmail("example");
        user.setPassword("password");
        userRepository.save(user);
        return "ok";
    }


    @RequestMapping(value = "/meets", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<Meet>> getMeets() {
        List<Meet> meets = meetRepository.findAll();
        if (!meets.isEmpty()) {
            logger.info("Meets found");
            return new ResponseEntity<List<Meet>>(meets, HttpStatus.OK);
        } else
            logger.info("No meets");
        return new ResponseEntity<List<Meet>>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/meets/name/{meetName}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<Meet> getMeetByName(@PathVariable("meetName") String name) {
        Meet meet = meetRepository.findByName(name);
        if (meet != null) {
            logger.info("Meet name founded");
            return new ResponseEntity<>(meet, HttpStatus.OK);
        } else {
            logger.info("Meet name isn't found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/meets/creator/{creatorId}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<Meet>> getMeetsByCreatorId(@PathVariable("creatorId") String creatorId) {
        List<Meet> meets = meetRepository.findByCreatorID(creatorId);
        if (!meets.isEmpty()) {
            logger.info("Meets by creator found");
            return new ResponseEntity<List<Meet>>(meets, HttpStatus.OK);
        } else {
            logger.info("Meets by creator weren't found");
            return new ResponseEntity<List<Meet>>(meets, HttpStatus.NO_CONTENT);
        }
    }//convert timestamp to date, work where there arent dates

    @RequestMapping(value = "/meets/add",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Meet> addMeet(@Valid @RequestBody Meet meet) {
        Meet newMeet = meetRepository.save(meet);
        return new ResponseEntity<>(newMeet, HttpStatus.OK);
    }//post doesnt work

    @RequestMapping(value = "/meets/{meetID}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<Meet> getMeetById(@PathVariable("meetID") String meetId) {
        Meet meet;
        Optional<Meet> meetOptional = meetRepository.findById(meetId);
        if (meetOptional.isPresent()) {
            meet = meetOptional.get();
            logger.info("Meet founded");
            return new ResponseEntity<Meet>(meet, HttpStatus.OK);
        } else {
            logger.info("Meet name isn't found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }//convert timestamp to date, work where there arent dates

    @RequestMapping(value = "/meets/{meetID}/addVotes", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<Meet> addVotetoMeet(@PathVariable("meetID") String meetId, @RequestBody MeetDataDto meetDataDto) {
        Meet oldMeet;
        Optional<Meet> meetOptional = meetRepository.findById(meetId);
        if (meetOptional.isPresent()) {
            oldMeet = meetOptional.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(meetDataDto.getFirstName())
                    .append(" ")
                    .append(meetDataDto.getLastName());
            String string = stringBuilder.toString();
            Map<String, int[]> newMap = new HashMap<>();
            if (meetDataDto.getVotes() != null) {
                logger.info("Putting in map");
                newMap.put(string, meetDataDto.getVotes());
                oldMeet.setVotes(newMap);
                meetRepository.save(oldMeet);
                return new ResponseEntity<>(oldMeet, HttpStatus.OK);
            } else {
                logger.info("votes is null");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("No meet with such id");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/meets/{meetId}/addDate",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Void> addDateToMeet(@PathVariable("meetId") String meetId, @RequestBody OffsetDateTime date) {
        Meet oldMeet;
        Optional<Meet> meetOptional = meetRepository.findById(meetId);
        if (meetOptional.isPresent()) {
            oldMeet = meetOptional.get();
            if (date != null) {
                Timestamp timestampDate = Timestamp.valueOf(date.toLocalDateTime());
                oldMeet.getDates().add(timestampDate);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.info("date is null");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } else {
            logger.info("no such meet");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/meets/delete/{meetId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("meetId") String meetId) {
        meetRepository.deleteById(meetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}