package com.kaidin.appframe.api.engine;

import com.kaidin.appframe.api.BaseRequestData;
import com.kaidin.appframe.api.BaseResponseData;
import com.kaidin.appframe.api.DispatchContext;
import com.kaidin.appframe.api.OpenApi;
import com.kaidin.appframe.api.annotation.Api;

public class ApiEngineImpl implements ApiEngine {

	@Override
	public <Res extends BaseResponseData, Req extends BaseRequestData> Res run(Class<? extends OpenApi<Res, Req>> apiClazz, Req requestData) {
		OpenApi<BaseResponseData, BaseRequestData> openApi = null;
		// apiClazz.getPackage().getAnnotation(annotationClass);
		Api api = apiClazz.getAnnotation(Api.class);
		String action = api.value();
		DispatchContext dispatchContent = new DispatchContext();

		run(openApi, dispatchContent);

		return (Res) dispatchContent.getResponseDate();
	}

	private void run(OpenApi openApi, DispatchContext dispatchContent) {
		if (null == openApi) {
			dispatchContent.setRequestDate(null);
			return;
		}

	}
}
