# jQuery Validation 動態驗證規則 (`rules('add')`) 教學筆記

## 1. `rules('add', {...})` 作用
- `rules('add', {...})` **用來動態新增表單驗證規則**，而不是直接寫在 HTML 中。
- **適用於需要根據不同情境改變驗證方式的表單**。

---

## 增加驗證規則(idNoCheck)
```javascript
$('[name="eorsUser.idNo"]').rules('add', {idNoCheck: true});
```

## 實作驗證規則(idNoCheck)
```javascript
  $.validator.addMethod("idNoCheck", function (userid, element) {
    var reg = /^[A-Z]{1}[1-2]{1}[0-9]{8}$/;  //身份證的正規表示式
    var other = /^[A-Z]{2}[0-9]{8}$/; //開頭為二碼英文+8碼數字跳過身分證字號檢核
    var reg2 = /^[A-Z]{1}[8-9]{1}[0-9]{8}$/;  //新版居留證表示式
    var skip = false;
    if (other.test(userid)) {
        skip = true;
    }

    if (!skip) {
        if (reg.test(userid) || reg2.test(userid)) {
            var s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  //把A取代成10,把B取代成11...的作法
            var ct = ["10", "11", "12", "13", "14", "15", "16", "17", "34", "18", "19", "20", "21",
                "22", "35", "23", "24", "25", "26", "27", "28", "29", "32", "30", "31", "33"];
            var i = s.indexOf(userid.charAt(0));
            var tempuserid = ct[i] + userid.substr(1, 9); //若此身份證號若是A123456789=>10123456789
            var num = tempuserid.charAt(0) * 1;
            for (i = 1; i <= 9; i++) {
                num = num + tempuserid.charAt(i) * (10 - i);
            }
            num += tempuserid.charAt(10) * 1;

            if ((num % 10) == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    } else {
        return true;
    }

}, $.validator.format("身分證字號檢核錯誤"));
```

## 實際範例
- 新增單一驗證規則
```javascript
  $('[name="eorsUser.idNo"]').rules('add', { required: true });
``` 

✅ 效果：設定 eorsUser.idNo 為必填。

- 新增多個驗證規則
```javascript 
- $('[name="eorsUser.idNo"]').rules('add', {
  required: true,   // 必填
  minlength: 10,    // 最少 10 個字
  idNoCheck: true   // 自訂身分證驗證
  });
``` 
✅ 效果：
- required: true → 必填
- minlength: 10 → 最少 10 個字
- idNoCheck: true → 使用自訂驗證（例如台灣身分證格式檢查）

- 移除特定驗證規則
```javascript 
$('[name="eorsUser.idNo"]').rules('remove', "idNoCheck");
``` 

- 移除所有驗證規則
```javascript 
$('[name="eorsUser.idNo"]').rules('remove');
``` 

## 6. 其他常見驗證規則

| 規則名稱           | 說明                   |
| ------------------ | ---------------------- |
| `required`         | 必填                   |
| `email`            | 必須是 Email 格式      |
| `url`              | 必須是合法網址         |
| `digits`           | 只能輸入數字           |
| `minlength: n`     | 最少輸入 `n` 個字      |
| `maxlength: n`     | 最多輸入 `n` 個字      |
| `equalTo: "#otherField"` | 必須與 `#otherField` 的值相同 |
