package webserver;

public class BiographyPage implements PageProvider{
    private final String fullName;
    private final String studentNo;

    public BiographyPage(String fullName,String studentNo){
        this.fullName = fullName;
        this.studentNo = studentNo;
    }

    @Override
    public String getHtml(){
        return "<!DOCTYPE html>"
                + "<html lang=\"tr\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<title>" + fullName + "</title>"
                + "<style>"
                + "body {"
                + "  margin: 0;"
                + "  min-height: 100vh;"
                + "  display: flex;"
                + "  justify-content: center;"
                + "  align-items: center;"
                + "  background: linear-gradient(135deg, #ffd6e8, #ffb3d1, #ff8fc0);"
                + "  font-family: 'Georgia', serif;"
                + "}"
                + ".kutu {"
                + "  background-color: rgba(255, 255, 255, 0.85);"
                + "  max-width: 650px;"
                + "  padding: 40px 50px;"
                + "  border-radius: 25px;"
                + "  border: 4px solid #ff69b4;"
                + "  box-shadow: 0 15px 40px rgba(199, 21, 133, 0.4);"
                + "  text-align: center;"
                + "}"
                + "h1 {"
                + "  font-family: 'Brush Script MT', 'Lucida Handwriting', cursive;"
                + "  font-size: 56px;"
                + "  font-style: italic;"
                + "  color: #c2185b;"
                + "  text-shadow: 3px 3px 6px rgba(194, 24, 91, 0.35);"
                + "  margin: 0 0 10px 0;"
                + "}"
                + "h2 {"
                + "  font-family: 'Trebuchet MS', sans-serif;"
                + "  font-size: 24px;"
                + "  color: #ad1457;"
                + "  text-shadow: 2px 2px 4px rgba(173, 20, 87, 0.25);"
                + "  margin: 0 0 25px 0;"
                + "}"
                + "p {"
                + "  color: #6a1b4d;"
                + "  font-size: 18px;"
                + "  line-height: 1.7;"
                + "}"
                + ".vurgu {"
                + "  color: #e91e63;"
                + "  font-weight: bold;"
                + "  font-style: italic;"
                + "}"
                + "hr {"
                + "  border: none;"
                + "  height: 3px;"
                + "  background: linear-gradient(to right, transparent, #ff69b4, transparent);"
                + "  margin: 20px 0;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"kutu\">"
                + "<h1>" + fullName + "</h1>"
                + "<h2>" + studentNo + "</h2>"
                + "<hr>"
                + "<p>Merhaba! Ben <span class=\"vurgu\">" + fullName + "</span>. "
                + "Yazım mühendisliği alanında eğitim görüyorum.2.sınıf Yazılım Mühendisliği öğrencisiyim. "
                + "Java programlama diline ve yazılım geliştirmeye büyük ilgi duyuyorum.Hedeflerim kendimi olabidiğince bu alanlarda geliştirmek.</p>"
                + "<p>Bu sayfa, <b>üçüncü parti hiçbir bileşen kullanılmadan</b> "
                + "yalnızca Java ve Socket programlama ile yazılmış bir web sunucusu tarafından sunulmaktadır.</p>"
                + "</div>"
                + "</body>"
                + "</html>";

    }
}
