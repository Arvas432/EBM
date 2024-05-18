
class RetrofitNetworkClient(private val iTunesService: ITunesApi): NetworkClient {
    override fun doRequest(dto: Any): Response {
        if(dto is ITunesSearchRequest){
            val resp = iTunesService.search(dto.expression).execute()
            val body = resp.body() ?: Response()
            return body.apply { resultCode = resp.code() }
        }else{
            return Response().apply { resultCode=400 }
        }
    }

}