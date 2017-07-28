var http = require("http");
var mysql = require("mysql");
var url = require("url");
var querystring = require("querystring");
var conInfo = {
	host : '127.0.0.1',
	user : 'root',
	password : 'mysql',
	port : '3306',
	database : 'myDb'
};
// 서버 생성
var server = http.createServer(function(request, response){
    // 1. 요청 url 분석 처리
    if(request.url.startsWith("/airbnb/house")){
        var parsedUrl = url.parse(request.url);
        var search = querystring.parse(parsedUrl.query);
        executeQuery(response, search);
    }else{
        response.writeHead(404,{"Content-Type":"text/html"});
        response.end("404 page not found!");
    }
});
// 2. 쿼리 실행
function executeQuery(response, search){
    var query = "select * from house";
    if(search){
        query = " select * from house where id not in "
              + " (select a.id "
              + "   from house a join reservation b "
              + "     on a.id = b.house_id "
              + "  where b.checkin between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or b.checkout between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or (b.checkin <= '"+search.checkin+"' and b.checkout >= '"+search.checkout+"') "
              + " ) ";
        if(search.guests > -1){
            query = query + " and guests > " + search.guests;
        }
        if(search.type > -1){
            query = query + " and type = " + search.type;
        }
        if(search.price_min > -1 && search.price_max > -1){
            query = query + " and price between " + search.price_min + " and " + search.price_max;
        }
        if(search.amenities > -1){ // wifi 여부만 체크
            query = query + " and amenities = " + search.amenities;
        }
        console.log("Query:"+query);
    }
    var con = mysql.createConnection(conInfo);
    con.connect();
    con.query(query, function(err, items, fields){ 
        if(err){
            console.log(err);
            sendResult(response, err);
        }else{
            console.log(items);
            sendResult(response, items);
        }
        this.end();
    });
}
// 3. 결과값 전송
function sendResult(response, data){
    response.writeHead(200,{"Content-Type":"text/html"});
    response.end(JSON.stringify(data));
}






// 서버 시작
server.listen(80, function(){
    console.log("server's running...");
});