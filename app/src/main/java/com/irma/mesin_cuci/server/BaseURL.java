package com.irma.mesin_cuci.server;

public class BaseURL {

        public static String baseUrl ="http://192.168.43.156:5050/";

        public static String login = baseUrl + "users/login";
        public static String register = baseUrl + "users/registrasi";

        //buku
    public static  String databarang = baseUrl +"msncuci/databarang";
    public static  String editdatabarang = baseUrl +"msncuci/ubah/";
    public static  String hapusData = baseUrl +"msncuci/hapus/";
    public static  String inputbarang = baseUrl +"msncuci/input";

    }

