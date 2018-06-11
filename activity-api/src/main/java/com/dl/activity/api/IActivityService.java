package com.dl.activity.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="activity-service")
public interface IActivityService {

}
