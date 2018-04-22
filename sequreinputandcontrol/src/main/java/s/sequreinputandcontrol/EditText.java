package s.sequreinputandcontrol;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class EditText extends android.support.v7.widget.AppCompatEditText {

    public EditText(Context context) {
        super(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    String TAG = "EditText Error:";

    private boolean validity = false;
    //private static String validSQL = new String();


    public static final int DEFAULT = 0;
    public static final int PASSWORD_INPUT = 1;
    public static final int SQL_INPUT = 2;
    public static final int EMAIL_INPUT = 3;
    public static final int URL_INPUT = 4;
    private int flag = 0;

    private int numberOfCharacters = 8;
    private boolean numbers = false;
    private boolean letters = false;
    private boolean capitals = false;
    private boolean specialCharacters = false;

    private boolean emailHost = false;
    private boolean emailDomain = false;

    private List<String> emailHostAllowed = new ArrayList<>();
    private List<String> emailHostDisabled = new ArrayList<>();
    private List<String> emailDomainAllowed = new ArrayList<>();
    private List<String> emailDomainDisabled = new ArrayList<>();

    private boolean urlProtocol = false;
    private boolean urlDomain = false;

    private List<String> urlProtocolAllowed = new ArrayList<>();
    private List<String> urlProtocolDisabled = new ArrayList<>();
    private List<String> urlDomainAllowed = new ArrayList<>();
    private List<String> urlDomainDisabled = new ArrayList<>();

    public boolean getValidity(){
        return validity;
    }

    public void setFunctionality(int flag){
        this.flag = flag;
    }

    public void setPasswordParameters(int numberOfCharacters){
        this.numberOfCharacters = numberOfCharacters;
    }

    public void setPasswordParameters(int numberOfCharacters, boolean numbers, boolean letters, boolean capitals, boolean specialCharacters){
        this.numberOfCharacters = numberOfCharacters;
        this.numbers = numbers;
        this.letters = letters;
        this.capitals = capitals;
        this.specialCharacters = specialCharacters;
    }

    public void setEmailParameters(boolean emailDomain, boolean emailHost){
        this.emailDomain = emailDomain;
        this.emailHost = emailHost;
    }

    public void setUrlParameters(boolean urlProtocol, boolean urlDomain) {
        this.urlProtocol = urlProtocol;
        this.urlDomain = urlDomain;
    }



    public void addUrlProtocolAllowed(String protocol){
        urlProtocolAllowed.add(protocol);
    }

    public void addUrlProtocolDisabled(String protocol){
        urlProtocolDisabled.add(protocol);
    }

    public void addUrlDomainAllowed(String protocol){
        urlDomainAllowed.add(protocol);
    }

    public void addUrlDomainDisabled(String domain){
        urlDomainDisabled.add(domain);
    }

    public void addEmailHostAllowed(String protocol){
        emailHostAllowed.add(protocol);
    }

    public void addEmailHostDisabled(String protocol){
        emailHostDisabled.add(protocol);
    }

    public void addEmailDomainAllowed(String protocol){
        emailDomainAllowed.add(protocol);
    }

    public void addEmailDomainDisabled(String protocol){
        emailDomainDisabled.add(protocol);
    }


    private boolean checkPassword(String password){
        if (password.isEmpty() )
            return false;
        if (password.length() < this.numberOfCharacters)
            return false;

        boolean containsNumber = true;
        boolean containsLetter = true;
        boolean containsCapital = true;
        boolean containsSpecialCharacter = true;
        boolean containsIllegal = false;

        if(numbers == true){
            containsNumber = false;
            char c = '0';
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < password.length(); j++) {
                    if (password.toCharArray()[j] == c) {
                        containsNumber = true;
                        break;
                    }
                    if (containsNumber == true)
                        break;
                }
                c++;
            }
        }

        if(letters == true){
            containsLetter = false;
            char c = 'a';
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < password.length(); j++) {
                    if (password.toCharArray()[j] == c) {
                        containsLetter = true;
                        break;
                    }
                    if (containsLetter == true)
                        break;
                }
                c++;
            }
        }

        if(capitals == true){
            containsCapital = false;
            char c = 'A';
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < password.length(); j++) {
                    if (password.toCharArray()[j] == c) {
                        containsCapital = true;
                        break;
                    }
                    if (containsCapital == true)
                        break;
                }
                c++;
            }
        }

        if(specialCharacters == true){
            containsSpecialCharacter = false;
            int i = 33;
            while(true) {
                for (int j = 0; j < password.length(); j++) {
                    if (password.toCharArray()[j] == i) {
                        containsSpecialCharacter = true;
                        break;
                    }
                    if (containsSpecialCharacter == true)
                        break;
                }
                i++;
                if (i == 48)
                    i = 58;
                if (i == 65)
                    i = 91;
                if (i == 97)
                    i = 123;
                if (i > 127)
                    break;
            }
        }

        {
            int i = 0;
            while(true) {
                for (int j = 0; j < password.length(); j++) {
                    if (password.toCharArray()[j] == i) {
                        containsIllegal = true;
                        break;
                    }
                    if (containsIllegal == true)
                        break;
                }
                i++;
                if (i == 10)
                    i = 11;
                if (i == 13)
                    i = 14;
                if (i == 33)
                    i = 127;
                if (i > 127)
                    break;
            }

        }


        if(containsNumber == true && containsLetter == true && containsCapital == true && containsSpecialCharacter == true && containsIllegal == false)
            return true;
        else
            return false;
    }




    private boolean checkEmail(String email){
        boolean validHost = false;
        boolean validDomain = false;



        if(emailHost){
            for(int i = 0; i<emailHostAllowed.size();i++){
                if (email.contains('@' + emailHostAllowed.get(i) + '.')){
                    validHost = true;
                    break;
                }
            }
        }
        else{
            validHost = true;
            for(int i = 0; i<emailHostDisabled.size();i++){
                if (email.contains('@' + emailHostDisabled.get(i) + '.')){
                    validHost = false;
                    break;
                }
            }
        }

        if(emailDomain){
            for(int i = 0; i<emailDomainAllowed.size();i++){
                if (email.contains('.' + emailDomainAllowed.get(i))){
                    validDomain = true;
                    break;
                }
            }
        }
        else{
            validDomain = true;
            for(int i = 0; i<emailDomainDisabled.size();i++){
                if (email.contains('.' + emailDomainDisabled.get(i))){
                    validDomain = false;
                    break;
                }
            }
        }

        if(validHost && validDomain && email.contains("@"))
            return true;
        else
            return false;
    }

    private boolean checkSQL(String sql){
        if(sql.contains("\'") || sql.contains("\"") || sql.contains("\\")
                || sql.contains("/") || sql.contains("%") || sql.contains("\'")
                || sql.contains("&") || sql.contains("#") || sql.contains("!"))
            return false;
        else
            return true;
    }

    private boolean checkUrl(String url){
        boolean validProtocol = false;
        boolean validFormat = false;
        boolean validDomain = false;

        if(urlProtocol){
            for(int i = 0; i<urlProtocolAllowed.size();i++){
                if (url.contains(urlProtocolAllowed.get(i) + "://")){
                    validProtocol = true;
                    break;
                }
            }
        }
        else{
            validProtocol = true;
            for(int i = 0; i<urlProtocolDisabled.size();i++){
                if (url.contains(urlProtocolDisabled.get(i) + "://")){
                    validProtocol = false;
                    break;
                }
            }
        }


        if(urlDomain){
            for(int i = 0; i<urlDomainAllowed.size();i++){
                if (url.contains('.' + urlDomainAllowed.get(i) + '/')){
                    validDomain = true;
                    break;
                }
            }
        }
        else{
            validDomain = true;
            for(int i = 0; i<urlDomainDisabled.size();i++){
                if (url.contains('.' + urlDomainDisabled.get(i))){
                    validDomain = false;
                    break;
                }
            }
        }

        if(validFormat && validDomain && validProtocol)
            return true;
        else
            return false;
    }

    @Override
    public Editable getText(){


        switch (flag){
            case DEFAULT:{
                validity = true;
            }break;
            case PASSWORD_INPUT:{
                if (checkPassword(super.getText().toString())) {

                    validity = true;
                }
                else {
                    validity = false;
                }
            }break;
            case SQL_INPUT:{
                if (checkSQL(super.getText().toString())) {
                    validity = true;
                }
                else{
                    validity = false;
                }
            }
            break;
            case EMAIL_INPUT:{
                if(checkEmail(super.getText().toString())){
                    validity = true;
                }
                else{
                    validity = false;
                }
            }
            break;
            case URL_INPUT:{
                if(checkUrl(super.getText().toString())){
                    validity = true;
                }
                else{
                    validity = false;
                }
            }
            break;
            default:
            {
                Log.v(TAG, "Invalid flag. Use method \"setFunctionality()\" with valid argument (such as EditText.PASSWORD_INPUT), or use default EditText");
            }
            break;

        }
        return super.getText();

    }
}