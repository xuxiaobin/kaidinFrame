package com.kaidin.appframe.api;

public interface OpenApi<Res extends BaseResponseData, Req extends BaseRequestData> {
	Res execute();
}
