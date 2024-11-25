package io.member;

import io.member.impl.FileMemberRepository;
import io.member.impl.MemoryMemberRepository;
import io.member.impl.ObjectMemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MemberConsoleMain {

   // private static final MemberRepository memberRepository = new MemoryMemberRepository();
  // private static final MemberRepository memberRepository = new FileMemberRepository();
   // private static final MemberRepository memberRepository = new DataMemberRepository();
    private static final MemberRepository memberRepository = new ObjectMemberRepository();

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. 회원 등록 | 2. 회원 목록 조회 | 3. 종료");
            System.out.print("선택 : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerMember(scanner);

                    break;
                case 2:
                    displayMember();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다. ");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력하세요. ");
            }
        }
    }

    private static void displayMember() {
        List<Member> members = memberRepository.findAll();
        System.out.println("회원 목록 : ");
        for (Member member : members) {
            System.out.printf("[ID : %s, Name : %s, Age : %d ]", member.getId(),member.getName(), member.getAge());
        }
        System.out.println();
    }

    private static void registerMember(Scanner scanner) {
        System.out.print("ID 입력 : ");
        String id = scanner.nextLine();

        System.out.print("Name 입력 : ");
        String name = scanner.nextLine();

        System.out.print("나이 입력 : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Member newMember = new Member(id, name, age);
        memberRepository.add(newMember);
        System.out.println("회원이 성공적으로 등록되었습니다.");
    }
}
