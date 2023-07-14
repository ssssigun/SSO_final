package kr.co.test.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/sample")
//@CrossOrigin(origins = {"http://172.19.48.1:5500/"})
public class SampleController {
	
	@ApiOperation("Sample GET doA")
	@GetMapping("/doA")
	public List<String> doA() {
		return Arrays.asList("AAA","BBB","CCC");
	}
}
