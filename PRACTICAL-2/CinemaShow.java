public class CinemaShow{
    private String title;
    private int seatAvailable;
    private final int capacity;
    private static int totalBooked=0;

    public CinemaShow(String title,int capacity){
    this.title=title;
    this.capacity=capacity;
    this.seatAvailable=capacity;                                  
    }

    public CinemaShow(String title){
      this(title,100);
    }

    public boolean  book(int n){
        if(n <= seatAvailable){
          seatAvailable-=n;
          totalBooked+=n;
          return true;
        }else{ 
       return false;
        }
    }

    public void cancel(int n){
        seatAvailable+=n;
        if(seatAvailable>capacity){
            seatAvailable=capacity;
        }
    }

    public int getSeatsAvailable(){
        return seatAvailable;
    }
    public static int getTotalBooked(){
        return totalBooked;
    }


    
public static void main(String[] args) {

    CinemaShow show = new CinemaShow("Avengers", 120);

    System.out.println(show.book(30));
    System.out.println(show.getSeatsAvailable());
    System.out.println("Total Booking: " + CinemaShow.getTotalBooked());

}
    
}