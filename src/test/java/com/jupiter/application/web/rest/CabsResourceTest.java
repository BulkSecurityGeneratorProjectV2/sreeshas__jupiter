package com.jupiter.application.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.application.Application;
import com.jupiter.application.config.MongoConfiguration;
import com.jupiter.application.domain.Cab;
import com.jupiter.application.domain.User;
import com.jupiter.application.repository.CabRepository;
import com.jupiter.application.repository.UserRepository;


import com.jupiter.application.security.AuthoritiesConstants;
import com.jupiter.application.service.CabService;
import com.jupiter.application.web.rest.dto.CabDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import java.net.URI;
import java.net.URL;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for Cabs Resource Test Controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
@Import(MongoConfiguration.class)
public class CabsResourceTest {


    private MockMvc restCabMockMvc;

    @Inject
    private CabService cabService;

    @Before
    public void setup() {
        CabsResource cabsResource = new CabsResource();
        ReflectionTestUtils.setField(cabsResource, "cabService", cabService);
        this.restCabMockMvc = MockMvcBuilders.standaloneSetup(cabsResource).build();
    }

    @Test
    public void testCreatingCab() throws Exception {
        CabDTO cab = new CabDTO();
        cab.setLongitude(37.37f);
        cab.setLatitude(34.34f);
        cab.setId(Long.parseLong("1234"));

        restCabMockMvc.perform(put("/cabs/{id}", 1234)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cab)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCab() throws Exception {

        restCabMockMvc.perform(delete("/cabs/{id}", 1234))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCabDetails() throws Exception {

        restCabMockMvc.perform(get("/cabs/{id}", 12345)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(12345))
                .andExpect(jsonPath("$.latitude").value(34.23))
                .andExpect(jsonPath("$.longitude").value(33.23));

    }

    public void testUpdatingCab() throws Exception {

    }



    public void testUnknownCabDetails() throws Exception {

    }

    public void testSearch() throws Exception {

    }


}
