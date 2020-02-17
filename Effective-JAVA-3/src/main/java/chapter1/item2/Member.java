package chapter1.item2;

public class Member {
    private String name;
    private String email;
    private String address;
    private String number;
    private int age;

    public Member(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.address = builder.address;
        this.number = builder.number;
        this.age = builder.age;
    }

    public static class Builder{
        private String name;
        private String email;

        private int age = 0;
        private String address;
        private String number;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder number(String number){
            this.number = number;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Member build(){
            if (name == null || email == null){
                throw new IllegalArgumentException("이름 혹은 이메일은 반드시 필요합니다.");
            }
            return new Member(this);
        }
    }
}
