package ekost;

import java.util.*;

public class main {
    
    record Kamar(String KodeKamar, String Fasilitas, int HargaPerBulan) {}
    record Penghuni(String KodeKamar, String Nama, String Alamat, String NomorHP, String TanggalPembayaran) {}
    record Feedback(String Subjek, String Isi) {}
    
    public static String[][] Login = function.LoginUser();
    
    public static void main(String[] args) {
        
        ArrayList<Kamar> kamar = function.ArrayKamar();
        ArrayList<Penghuni> penghuniList = function.ArrayPenghuni();
        ArrayList<Feedback> feedbackList = function.ArrayFeedback();
        
        Stack<Kamar> kamarStack = new Stack<>();
        for (Kamar kamarstack : kamar) {
            kamarStack.push(kamarstack);
        }
        
        Kamar kamarremove1 = new Kamar("02", "Kamar 2x3 + Kasur + Lemari + Kamar Mandi luar", 600000);
        Kamar kamarremove2 = new Kamar("03", "Kamar 2,5x3 + Kasur + Lemari + Kamar Mandi luar", 700000);
        Kamar kamarremove3 = new Kamar("04", "Kamar 1,5x3 + Kasur + Lemari + Kamar Mandi luar", 450000);
        Kamar kamarremove4 = new Kamar("06", "Kamar 2x3 + Kasur + Lemari + Kamar Mandi luar", 600000);
    
        kamarStack.removeIf(
            kamarRemove -> kamarRemove.equals(kamarremove1)
                    || kamarRemove.equals(kamarremove2)
                    || kamarRemove.equals(kamarremove3)
                    || kamarRemove.equals(kamarremove4)
        );
        
        Scanner input = new Scanner(System.in);
        
        boolean ulangLogin = true;
        boolean ulangGuest = true;
        boolean ulangAdmin = true;
        boolean ulangUser = true;
               
        // MENU LOGIN
        while (ulangLogin) {

            ulangLogin = true;
            ulangGuest = true;
            ulangAdmin = true;
            ulangUser = true;


            function.MenuLogin();
            
            System.out.print("=>> ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {

                case 3: // 3. Guest
                System.out.println("Anda masuk sebagai guest.");

                // MENU GUEST

                    while (ulangGuest) {

                        function.MenuGuest();

                        System.out.print("=>> ");
                        int GuestMenuAwal = input.nextInt();
                        input.nextLine();

                        switch (GuestMenuAwal) {
                            case 1: // 1. Peraturan dan Syarat Daftar Kost
                                function.PeraturanKost();
                                function.SyaratDaftar();

                                ulangGuest = function.UlangMenu();
                                if (!ulangGuest) {
                                    ulangLogin = false; // add this line
                                }
                                break;

                            case 2: // 2. Daftar dan Harga Kamar
                                
                                function.DaftarKamarTersedia(kamarStack);
                                
                                ulangGuest = function.UlangMenu();
                                if (!ulangGuest) {
                                    ulangLogin = false; // add this line
                                }
                                break;

                            case 3: // 3. Daftar menjadi Penghuni Kost
                                
                                function.DaftarKamarTersedia(kamarStack);
                                
                                System.out.print("Masukkan kode kamar yang ingin anda huni : ");
                                String KodeKamar = input.next().toUpperCase();
                                input.nextLine();
                                
                                function.inputInformasiPenyewa(input, KodeKamar);
                                
                                System.out.println("Lanjut melakukan pembayaran? (Y/T)");
                                System.out.print("=>> ");
                                String LanjutBayar = input.nextLine().toUpperCase();

                                if (LanjutBayar.equals("Y")) {
                                    boolean cocok = false;
                                    for (Kamar kamarstack : kamarStack) {
                                        if (kamarstack.KodeKamar().equals(KodeKamar)) {
                                            function.Pembayaran(kamarstack, input);
                                            ulangGuest = function.UlangMenu();
                                            cocok = true;
                                            break;
                                        }
                                    }
                                    if (!cocok) {
                                        System.out.println("Kode Kamar yang anda masukkan tidak terdaftar.");
                                        ulangGuest = function.UlangMenu();
                                        if (!ulangGuest) {
                                            ulangLogin = false; // add this line
                                        }
                                    }
                                } else {
                                    ulangGuest = function.UlangMenu();
                                    if (!ulangGuest) {
                                        ulangLogin = false; // add this line
                                    }
                                }
                                break;
                                
                            case 4: // 4. Informasi Pemilik Kost
                                
                                function.InfoPemilik();
                                
                                ulangGuest = function.UlangMenu();
                                if (!ulangGuest) {
                                    ulangLogin = false; // add this line
                                }
                                break;

                            case 5: // 5. Bantuan
                                
                                function.Bantuan();
                                
                                ulangGuest = function.UlangMenu();
                                if (!ulangGuest) {
                                    ulangLogin = false; // add this line
                                }
                                break;

                            case 6: // 6. Logout
                                System.out.println("Anda telah logout.");
                                ulangGuest = false;
                                ulangLogin = true;
                                break;
                                
                            default: // Jika salah masukkan angka
                                System.out.println("Angka yang anda masukkan tidak sesuai.");

                                ulangGuest = function.UlangMenu();
                                if (!ulangGuest) {
                                    ulangLogin = false; // add this line
                                }
                                break;
                        }
                    }
                    break;

                case 4: // 4. Bantuan
                                
                    function.Bantuan();
                    
                    ulangLogin = function.UlangMenu();
                    break;
                        
                default:
                System.out.print("Masukkan Username : ");
                String username = input.next().toUpperCase();
                input.nextLine();
                System.out.print("Masukkan Password : ");
                String password = input.next().toUpperCase();

                String result = function.LoginMenu(username, password);
                if ("Admin".equals(result) || "User".equals(result)) {
                    String role = result;
                    String user = function.LoginUsername(username, password);
                    Kamar infoKamar = function.InfoKamar(user, penghuniList, kamar);
                    
                    if (role != null) {
                        System.out.println("Anda berhasil login sebagai " + role + ".");

                    // MENU ADMIN (PEMILIK KOST)

                        if ("Admin".equals(role)) {

                            System.out.println("Selamat datang, " + username + "!");

                            while (ulangAdmin) {

                                function.MenuAdmin();

                                System.out.print("=>> ");
                                int AdminMenuAwal = input.nextInt();

                                switch (AdminMenuAwal) {

                                    case 1: // 1. Daftar Kamar

                                        function.DaftarKamar();
                                        
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 2: // 2. Daftar Penghuni Kost

                                        function.DaftarPenghuni(penghuniList);
                                        
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                        
                                    case 3: // 3. Daftar Username dan Password Kamar
                                        
                                        function.DaftarAkun();
                                        
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                        
                                                                        
                                    case 4: //4. Feedback Penghuni
                                        function.Feedback(input, feedbackList);
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 5: // 5. Tambah Penghuni Kost

                                        function.DaftarKamarTersedia(kamarStack);

                                        function.TambahPenghuni(input, penghuniList, kamarStack);
                                        
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 6: // 6. Hapus Penghuni Kost

                                        function.DaftarPenghuni(penghuniList);
                                        
                                        System.out.print("Masukkan kode kamar yang ingin dihapus penghuninya : ");
                                        String HapusKodeKamar = input.next();
                                        input.nextLine();

                                        boolean cocokHapus = false;
                                        for (Penghuni listPenghuni : penghuniList) {
                                            if (listPenghuni.KodeKamar().equals(HapusKodeKamar)) {
                                                cocokHapus = true;
                                                break;
                                            } else {
                                                System.out.println("Kode kamar tidak ditemukan.");
                                                break;
                                            }
                                        }

                                        if (cocokHapus) {
                                            System.out.println("konfirmasi penghapusan penghuni kost? (Y/T)");
                                            String HapusJawab = input.nextLine().toUpperCase();

                                            if (HapusJawab.equals("Y")) {
                                                penghuniList.removeIf(listPenghuni -> listPenghuni.KodeKamar().equals(HapusKodeKamar));
                                                for (Kamar Stackkamar : kamar) {
                                                    if (Stackkamar.KodeKamar().equals(HapusKodeKamar) && !kamarStack.contains(Stackkamar)) {
                                                    kamarStack.push(Stackkamar);
                                                    System.out.println("=================================================");
                                                    System.out.println("|  Penghuni dengan kode kamar " + HapusKodeKamar + " telah dihapus  |");
                                                    System.out.println("=================================================");
                                                    }
                                                }
                                            }
                                        }

                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 7: // 7. Bantuan
                                    
                                        function.Bantuan();
                                        
                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 8: // 8. Logout
                                        System.out.println("Anda telah logout.");
                                        ulangAdmin = false;
                                        ulangLogin = true;
                                        break;

                                    default: // Jika salah masukkan angka
                                        System.out.println("Angka yang anda masukkan tidak sesuai.");

                                        ulangAdmin = function.UlangMenu();
                                        if (!ulangAdmin) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                }
                            }

                    // MENU USER (PENGHUNI KOST)

                        } else if ("User".equals(role)) {

                            System.out.println("Selamat datang, " + user + "!");

                            while (ulangUser) {

                                function.MenuUser();

                                System.out.print("=>> ");
                                int UserMenuAwal = input.nextInt();
                                input.nextLine();

                                switch (UserMenuAwal) {

                                    case 1: // 1. Peraturan Penghuni Kost
                                        
                                        function.PeraturanKost();

                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 2: // 2. Informasi Kamar
                                        
                                        function.InformasiKamar(infoKamar);

                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                
                                    case 3: // 3. Pembayaran

                                        function.Pembayaran(infoKamar, input);
                                        
                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                        
                                    case 4: //4. Tambah Feedback
                                        
                                        function.TambahFeedback(input, feedbackList);
                                        
                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                        
                                    case 5: // 5. Informasi Pemilik Kost
                                        
                                        function.InfoPemilik();
                                        
                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                        
                                    case 6: // 6. Bantuan
                                    
                                        function.Bantuan();
                                        
                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;

                                    case 7: // 7. Logout
                                        System.out.println("Anda telah logout.");
                                        ulangUser = false;
                                        ulangLogin = true;
                                        break;

                                    default: // Jika salah masukkan angka
                                        
                                        System.out.println("Angka yang anda masukkan tidak sesuai.");

                                        ulangUser = function.UlangMenu();
                                        if (!ulangUser) {
                                            ulangLogin = false; // add this line
                                        }
                                        break;
                                }
                            }
                        }
                    }
                } else {
                    System.out.println(result);
                }
                break;
            }
        }
    }
}