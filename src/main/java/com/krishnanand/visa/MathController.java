package com.krishnanand.visa;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author krishnanand (Kartik Krishnanand)
 */
@RestController("/visa")
public class MathController {

  private boolean isInvalid(String a) {
    return a == null || a.isEmpty();
  }
  @RequestMapping(value="/multiply", method=RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> multiply(
      HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    if (isInvalid(request.getParameter("a")) || isInvalid(request.getParameter("b")) ||
        isInvalid(request.getParameter("key"))) {
      map.put("status", "error");
      map.put("message", "Bad Inputs");
      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.UNAUTHORIZED);
    }
    int a = Integer.parseInt(request.getParameter("a"), 10);
    int b = Integer.parseInt(request.getParameter("b"), 10);
    int key = Integer.parseInt(request.getParameter("key"), 10);
    if (key % 2 != 0) {
      map.put("status", "error");
      map.put("message", "Bad Inputs");
      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.UNAUTHORIZED);
    }
    int output = a * b;
    Map<String, String> response = new HashMap<>();
    response.put("InputA", String.valueOf(a));
    response.put("InputB", String.valueOf(b));
    map.put("inputs", response);
    map.put("status", "success");
    map.put("code", "200");
    map.put("result", output);
    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
  }
  
  @JsonInclude(Include.NON_NULL)
  private static class Body {
    
    private Integer a;
    
    private Integer b;

    public Integer getA() {
      return a;
    }

    public void setA(Integer a) {
      this.a = a;
    }

    public Integer getB() {
      return b;
    }

    public void setB(Integer b) {
      this.b = b;
    }
  }
  
  @RequestMapping(value="/divide", method=RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> divide(
      @RequestBody Body body , HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    if (body.getA() == null || body.getB() == null || isInvalid(request.getHeader("X-KEY-VAL"))) {
      map.put("status", "error");
      map.put("message", "Bad Inputs");
      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.UNAUTHORIZED);
    }
    int a = body.getA();
    int b = body.getB();
    int key = Integer.parseInt(request.getHeader("X-KEY-VAL"), 10);
   
    if (key % 2 != 0) {
      map.put("status", "error");
      map.put("code", "401");
      map.put("message", "Wrong Key");
      return new ResponseEntity<Map<String,Object>>(map, HttpStatus.UNAUTHORIZED);
    }
    double output = (double) ((double) a / b);
    Map<String, String> response = new HashMap<>();
    response.put("InputA", String.valueOf(body.getA()));
    response.put("InputB", String.valueOf(body.getB()));
    map.put("inputs", response);
    map.put("status", "success");
    map.put("code", HttpStatus.OK);
    map.put("result", output);
    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
  }

}
