
package edu.tcu.cs.backend.CrewedUser;

import edu.tcu.cs.backend.System.Result;
import edu.tcu.cs.backend.System.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}")
public class crewedUserController {

    private final crewedUserService crewedUserService;

    public crewedUserController(crewedUserService crewedUserService) {
        this.crewedUserService = crewedUserService;
    }


}


