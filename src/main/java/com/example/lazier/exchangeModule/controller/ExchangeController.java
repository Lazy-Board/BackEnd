package com.example.lazier.exchangeModule.controller;

import com.example.lazier.exchangeModule.dto.ExchangeDto;
import com.example.lazier.exchangeModule.dto.PartialExchangeDto;
import com.example.lazier.exchangeModule.service.ExchangeService;
import com.example.lazier.exchangeModule.service.PartialExchangeService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    private final PartialExchangeService partialExchangeService;

    // 실시간 환율 정보 저장
    @ApiOperation(value = "실시간 환율 정보를 DB에 저장")
    @PostMapping
    public ResponseEntity<?> addExchangeInfo(HttpServletRequest request,
        @Valid ExchangeDto exchangeDto, PartialExchangeDto partialExchangeDto) {
        String userId = request.getAttribute("userId").toString();
        exchangeService.add(exchangeDto, userId);
        partialExchangeService.partialAdd(partialExchangeDto, userId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 메인 화면에서 볼 수 있는 환율 정보 조회
    // (4가지 정보만 조회 가능 : 통화명, 매매기준율, 전일대비, 등락율) - 실제 적용
    @ApiOperation(value = "메인화면 환율 창에서 환율 정보 조회(통화명, 매매기준율, 전일대비, 등락율)")
    @GetMapping("/search")
    public ResponseEntity<?> exchangePartialInfo(
        HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        return new ResponseEntity<>(
            partialExchangeService.getExchangePartialInfo(userId), HttpStatus.OK);
    }

    // 상세정보 조회 (특정 통화의 상세정보)
    @ApiOperation(value = "특정 통화의 상세정보 조회")
    @GetMapping("/search/{currencyName}")
    public ResponseEntity<?> exchangeCurrencyNameDetail(
        HttpServletRequest request, @PathVariable String currencyName) {
        String userId = request.getAttribute("userId").toString();
        return new ResponseEntity<>(
            exchangeService.getExchangeCurrencyDetail(userId, currencyName), HttpStatus.OK);
    }

    // 상세정보 리스트 화면 (10가지 정보 모두 포함)
    @ApiOperation(value = "국가별 환율 상세정보 조회")
    @GetMapping("/list")
    public ResponseEntity<?> exchangeList(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        return new ResponseEntity<>(exchangeService.getExchangeList(userId), HttpStatus.OK);
    }

    // update
    @ApiOperation(value = "환율정보 업데이트")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateExchange(
        @RequestBody @PathVariable String userId,
        ExchangeDto exchangeDto, PartialExchangeDto partialExchangeDto) {
        exchangeService.updateRealTimeExchange(userId, exchangeDto);
        partialExchangeService.updateRealTimePartialExchange(userId, partialExchangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete
    @ApiOperation(value = "환율정보 삭제")
    @DeleteMapping( "/{userId}")
    public ResponseEntity<?> deleteExchange(@PathVariable String userId) {
        exchangeService.deleteExchange(userId);
        partialExchangeService.deletePartialExchange(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
