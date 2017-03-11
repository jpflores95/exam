package net.tutorial.utilities;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

public class SpeechToTextService {
		SpeechToText service;
		
		public SpeechToTextService() {
			EnvVariables envVar = new EnvVariables();
			Map<String, String> creds = envVar.getCredentials("speech_to_text");
			service = new SpeechToText(creds.get("username"), creds.get("password") );
		} 
		
		public String getTranscription(File audioFile) {
			String text = null;
			
			RecognizeOptions options = new RecognizeOptions.Builder()
					  .continuous(true)
					  .interimResults(true)
					  .contentType(HttpMediaType.AUDIO_WAV)
					  .build();
			
			
			SpeechResults output = service.recognize(audioFile, options).execute();
			List<Transcript> transcriptList = output.getResults();
			
			 if (transcriptList.size() > 0) {
	                List<SpeechAlternative> speechList = transcriptList.get(0).getAlternatives();
	                if (speechList.size() > 0){
	                    text = speechList.get(0).getTranscript();
	                }
	            }
			
			 audioFile.delete();
			 
			return text;
		}
}
