package mokito;

public class Item {
    public BoxRepo boxRepo;

    public Item(BoxRepo boxRepo) {
        this.boxRepo = boxRepo;
    }

    public void rent(Box box){
        Box save = boxRepo.save(box);
        System.out.println("rent: " + save.getTitle());
    }


}
