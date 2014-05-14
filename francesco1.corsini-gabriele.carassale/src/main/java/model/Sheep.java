package model;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.896DE4C4-9019-244D-8B50-33CA6246F6EA]
// </editor-fold> 
public class Sheep extends Animal {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0621A386-6668-9DF2-79EC-03F2889EA589]
    // </editor-fold> 
    private int age;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BAEA905D-D3D7-56E9-53F5-E89F15ED5A18]
    // </editor-fold> 
    private String sex;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FD70F186-957E-9DC5-7767-817EBEBBA57C]
    // </editor-fold> 
    public Sheep (Terrain terrain, boolean initialization) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DBA0C265-E4C5-F2EA-A3EB-4F8F2F0A58FB]
    // </editor-fold> 
    public int getAge () {
        return age;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FA264577-A7B1-D14B-98BB-172EE496574B]
    // </editor-fold> 
    public void setAge (int val) {
        this.age = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C8983F32-93B8-7D83-A4BC-2B5B6C7E227B]
    // </editor-fold> 
    public String getSex () {
        return sex;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C51DA34C-3FB4-EEE6-4EB5-6CC473350DA2]
    // </editor-fold> 
    public void setSex (String val) {
        this.sex = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0600B7D1-0C50-E7CC-5B63-090B49763920]
    // </editor-fold> 
    public void growUpOneTurn () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.048E7B67-E35D-8B2A-6DFF-100C7DD3ABE1]
    // </editor-fold> 
    public boolean isWhiteSheep () {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FC9C9215-A5A0-DEF4-D2C7-80C0F937B039]
    // </editor-fold> 
    public boolean isLamb () {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B81915B9-A115-ABA3-9B60-FF5F01D6BB5A]
    // </editor-fold> 
    public boolean isRam () {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B212A9DD-E01D-C731-3BB5-65AF44A55F59]
    // </editor-fold> 
    private void becameOld () {
    }

}

