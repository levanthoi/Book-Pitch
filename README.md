#### Xem qua mấy video trên youtube họ làm như nào rồi làm theo.

# Các giai đoạn phát triển
  1. Design các feature trong 2-3 tuần
  2. Test và sửa đổi (1 tuần)
  3. Liên kết các feature screen lại với nhau - 1 tuần
  4. Connect DB - 1-2 tuần
  5. Kiểm tra và sửa đổi

# Mọi create branch và mọi commit phải gán mã ticket Jira vào đầu. Cách đơn giản nhất là vào trang jira của nhóm, chọn task nào làm thì bấm vào nó sẽ hiện ra 1 dialog bên phải có phần create branch, rồi bấm vào thì chọn phần base from là từ thằng cha nó là nhánh dev với feature chọn từ epic


## Design tham khảo

- https://dribbble.com/shots/9912186-Sports-Groung-Booking-App-UI-Kit/attachments/11431987?mode=media 
- https://dribbble.com/shots/21117000-Playground-Booking-App-UI-Kit 
- https://www.behance.net/gallery/173301531/Sports-Booking-Mobile-UI-App-Design?tracking_source=search_projects|sports+booking&l=13 
- https://www.behance.net/gallery/179625661/Badminton-Club-Booking-App-UI-Design?tracking_source=search_projects|sports+booking&l=17 
- https://www.behance.net/gallery/185552065/Turf-Time-Turf-Boking-App-UXUI-Case-study?tracking_source=search_projects|sports+booking&l=6 
- https://www.behance.net/gallery/159703287/KheloMore-Turf-Court-Booking?tracking_source=search_projects|sports+booking&l=16 
- https://www.behance.net/search/projects/sports%20booking 

## web tham khảo thanh toán:

- https://turfbookingbd.com/ 
<br>

## app tham khảo (quan trọng): 

- malaebapp (https://malaebapp.com/)
  
<br>

## WorkFlow GIT

-	Mọi feature đều phải checkout từ nhánh dev ra.
-	Ban đầu khi nhận đc 1 feature mới, thì sẽ checkout về dev => Rồi từ branch dev, git checkout -b ten-nhanh(VD:git checkout -b TLU-11-Login)
-	git add . : Sau khi hoàn thành feature xong thì git add .
-	git commit -m “TLU-11 completed ui login”
-	git pull origin dev
-	git push origin ten-nhanh-dang-lam-viec
-	Làm xong feature nào đấy thì tạo pull request luôn trên github.
-	Nếu tạo xong mà còn cần thay đổi nào đó trên nhánh vừa tạo pull request(ví dụ làm chức năng đăng kí, mà ban đầu chỉ có password không có confirm password), sau khi tạo pull request thì nhớ ra là thiếu field cf passw, thì sửa đổi lại và sau khi sửa xong, thì chạy lệnh git commit –amend. (:wq). Mục đích là để không phải lưu commit khác trong pull request đã tạo trước đó. => git push origin ten-nhanh-dang-lam -f 
-	Lưu ý: Theo chuẩn mình làm là 1 pull request chỉ chứa 1 commit
•	Trường hợp:
•	- git log –online
•	Khôi phục branch đã xóa: git checkout -b ten-nhanh mã-hash
