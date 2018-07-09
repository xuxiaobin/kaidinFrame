package com.kaidin.appframe.api.engine;

import com.kaidin.appframe.api.OpenApi;
import com.kaidin.appframe.api.BaseRequestData;
import com.kaidin.appframe.api.BaseResponseData;

public interface ApiEngine {
	<Res extends BaseResponseData, Req extends BaseRequestData> Res run(Class<? extends OpenApi<Res, Req>> apiClazz, Req requestData);
}
