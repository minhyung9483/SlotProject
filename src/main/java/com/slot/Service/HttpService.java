package com.slot.Service;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class HttpService {
    private static final String TAG="HTTP_SERVICE";
    public void get(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정

            con.setRequestMethod("GET");



            //URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다. URL 연결은 입출력에 사용될 수 있다. URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.
            con.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void post(String strUrl, String jsonMessage){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정

            con.setRequestMethod("POST");

            //json으로 message를 전달하고자 할 때
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(jsonMessage); //json 형식의 message 전달
            wr.flush();

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }


    private static final RestTemplate REST_TEMPLATE;

    static {
        // RestTemplate 기본 설정을 위한 Factory 생성
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.

        REST_TEMPLATE = new RestTemplate(factory);
    }

    public String dxfParseApi(String url, String metas, List<MultipartFile> files) {

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
//                    map.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
                map.add("files", file.getResource());
                // 최근 Spring 버전을 쓴다면 map.add("files", file.getResource()); 로 변경
            }
        }
        map.add("metas",metas);
        System.out.println(metas);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
//            HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(new FileSystemResource(file));
        ResponseEntity<String> postForEntity = REST_TEMPLATE.exchange(url, HttpMethod.POST, httpEntity, String.class);

        System.out.println(postForEntity.getBody());
        return postForEntity.getBody();
    }

}
