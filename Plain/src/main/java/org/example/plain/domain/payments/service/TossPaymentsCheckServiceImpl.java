package org.example.plain.domain.payments.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.example.plain.domain.groupmember.service.GroupMemberService;
import org.example.plain.domain.lecture.lectureMember.interfaces.LectureMemberService;
import org.example.plain.domain.lecture.lectureMember.service.LectureMemberImpl;
import org.example.plain.domain.payments.domain.Payments;
import org.example.plain.domain.payments.domain.PaymentsToss;
import org.example.plain.domain.payments.interfaces.PaymentsCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TossPaymentsCheckServiceImpl implements PaymentsCheckService {

    @Value("${spring.payKey}")
    private String payKey;

    @Override
    public boolean CheckingForPayments(Payments payments) throws URISyntaxException {
        PaymentsToss paymentsToss = (PaymentsToss) payments;


        payKey = Base64.getEncoder().encodeToString(paymentsToss.getPaymentsKey().getBytes());

        MultiValueMap<String,String> map = new HttpHeaders();
        map.add("Authorization", "Basic " + payKey);
        map.add("Content-Type", "application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("payKey", payKey);
        jsonObject.addProperty("orderId",paymentsToss.getOrderId());


        RequestEntity requestEntity = new RequestEntity(paymentsToss, map,HttpMethod.GET, new URI("https://api.tosspayments.com/v1/payments/confirm"));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MultiValueMap> response = restTemplate.exchange(requestEntity, MultiValueMap.class);

        if(response.getStatusCode().is4xxClientError()||response.getStatusCode().is5xxServerError()) {
            throw new URISyntaxException(response.getStatusCode().toString(),response.getBody().get("message").toString());
        }

        return true;
        // 결제 완료 후 결제 정보저장, 해당 강의의 수강자로 가입, 리다이렉트.

    }


}
