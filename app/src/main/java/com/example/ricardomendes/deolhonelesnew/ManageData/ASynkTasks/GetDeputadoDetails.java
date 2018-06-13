package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Legislatura;
import com.example.ricardomendes.deolhonelesnew.Model.Gabinete;
import com.example.ricardomendes.deolhonelesnew.Model.UltimoStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class GetDeputadoDetails extends AsyncTask<Void, Void, Deputado> {

        private GetDeputadoDetailsCallback callback;
        private String idDeputado;

        public GetDeputadoDetails(String id, @NonNull GetDeputadoDetailsCallback callback){
            this.callback = callback;
            this.idDeputado = id;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected  Deputado doInBackground(Void... total){
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(Constants.URL_DEPUTADO_DETAIL + idDeputado);
            Deputado deputado = new Deputado();
            if(jsonStr!=null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject deputadoDetail = new JSONObject(jsonObj.getString("dados"));
                    JSONObject ultimoStatusJson = new JSONObject(deputadoDetail.getString("ultimoStatus"));
                    JSONObject gabineteJson = new JSONObject(ultimoStatusJson.getString("gabinete"));
                    deputado.setId((int) deputadoDetail.getLong("id"));
                    deputado.setNome(ultimoStatusJson.getString("nomeEleitoral"));
                    deputado.setSiglaPartido(ultimoStatusJson.getString("siglaPartido"));
                    deputado.setSiglaUf(ultimoStatusJson.getString("siglaUf"));
                    deputado.setDataNascimento(deputadoDetail.getString("dataNascimento"));
                    deputado.setEscolaridade(deputadoDetail.getString("escolaridade"));
                    deputado.setMunicipioNascimento(deputadoDetail.getString("municipioNascimento"));
                    deputado.setUfNascimento(deputadoDetail.getString("ufNascimento"));
                    deputado.setUri(deputadoDetail.getString("uri"));
                    deputado.setUriPartido(ultimoStatusJson.getString("uriPartido"));
                    Legislatura legislatura = new Legislatura();
                    legislatura.setId(ultimoStatusJson.getInt("idLegislatura"));
                    String jsonLegislatura = sh.makeServiceCall(Constants.URL_LEGISLATURA + legislatura.getId());
                    if(jsonLegislatura!=null) {
                        JSONObject objLegislatura = new JSONObject(jsonLegislatura);
                        objLegislatura = new JSONObject(objLegislatura.getString("dados"));
                        legislatura.setDataInicio(objLegislatura.getString("dataInicio"));
                        legislatura.setDataFim(objLegislatura.getString("dataFim"));
                        legislatura.setUri(objLegislatura.getString("uri"));
                        deputado.setLegislatura(legislatura);
                    }
                    deputado.setUrlFoto(ultimoStatusJson.getString("urlFoto"));
                    UltimoStatus ultimoStatus = new UltimoStatus();
                    ultimoStatus.setSituacao(ultimoStatusJson.getString("situacao"));
                    ultimoStatus.setCondicaoEleitoral(ultimoStatusJson.getString("condicaoEleitoral"));
                    Gabinete gabinete = new Gabinete();
                    gabinete.setNome(gabineteJson.getString("nome"));
                    gabinete.setPredio(gabineteJson.getString("predio"));
                    gabinete.setSala(gabineteJson.getString("sala"));
                    gabinete.setAndar(gabineteJson.getString("andar"));
                    gabinete.setTelefone(gabineteJson.getString("telefone"));
                    gabinete.setEmail(gabineteJson.getString("email"));
                    ultimoStatus.setGabinete(gabinete);
                    deputado.setUltimoStatus(ultimoStatus);
                    sh.makeServiceCall(Constants.URL_PROPOSICOES_ID + deputado.getId() + Constants.ORDENAR_ID);
                    Map<String,String> header = sh.getHeader();
                    int totalProposicoes = Integer.parseInt(header.get("x-total-count"));
                    deputado.setQtdeProjetos(totalProposicoes);
                }catch (final JSONException e){
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }else{
                Log.e(TAG, "Nao pegou nenhum Json do servidor");
            }
            return deputado;
        }

        @Override
        protected void onPostExecute(Deputado result){
            super.onPostExecute(result);
            callback.onDeputadoDetails(result);
        }
}
