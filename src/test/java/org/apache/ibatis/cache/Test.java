package org.apache.ibatis.cache;

/**
 * @author Gin
 * @description
 * @date 2023/7/18 11:38
 */
public class Test {
    static class Body implements Cloneable {
        public Head head;
        public Foot foot;

        public Body() {
        }

        public Body(Head head,Foot foot) {
            this.head = head;
            this.foot = foot;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Body newBody = (Body) super.clone();
            //重写副本深拷贝
            newBody.head = (Head) head.clone();
            // 浅拷贝
            newBody.foot = foot;
            return newBody;
        }
    }

    static class Head implements Cloneable {
        public Face face;

        public Head() {
        }

        public Head(Face face) {
            this.face = face;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    static class Foot implements Cloneable {

        public Foot() {
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    static class Face implements Cloneable {
        public Face() {
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Body body = new Body(new Head(),new Foot());
        Body body1 = (Body) body.clone();
        System.out.println("body == body1 : " + (body == body1));
        System.out.println("body.head == body1.head : " + (body.head == body1.head));
        System.out.println("body.foot == body1.foot : " + (body.foot == body1.foot));
    }
}