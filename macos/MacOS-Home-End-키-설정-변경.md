# 맥 OS 에서 Home,End 키

별 기대를 안하고 적용했는데 너무 만족하고 잘 쓰고 있다. 윈도우, 리눅스에서는 모두 Ctrl 을 통해서 단어 사이를 이동하는데 MacOS 만 지 혼자 Opt 키를 쓴다는건 가끔 빡칠때가 있었다. 그런데 trusktr 이라는 github 유저가 작성한 DefaultKeyBinding 을 적용해보니 Window,Linux 에서 사용하는 키 매핑을 그대로 사용할 수 있다고 느꼈다. 약간은 보편적이고, 상식적인 키 바인딩 같다.<br/>

참고로 내 경우에는 Ctrl,Cmd,Option 키의 매핑은 시스템 설정에서 아래와 같이 바꿔준 상태에서 진행했다. (시스템 환경설정 \> 키보드 \> `키보드 단축키 ..` 버튼 클릭 \> 보조키 \> 우측 상단 키보드 선택 )

- Caps Lock : Caps Lock
- **Control 키 : Command**
- **Option 키 : ^ Control 키**
- **Command 키 : Option 키**
- 기능 (fn) 키 : fn 기능

<br/>



# 참고자료

- [macOS 의 Home, End 키 설정 변경](https://andrewpage.tistory.com/71)
- [trusktr/DefaultKeyBinding.dict](https://gist.github.com/trusktr/1e5e516df4e8032cbc3d)

<br/>



# Home, End 키 설정 변경

처음에는 [macOS 의 Home, End 키 설정 변경](https://andrewpage.tistory.com/71) 을 보고 `오? 이거 괜찮네?` 했었다. 그리고 막상 적용하려고 하니까 Github 에 방문해서 원래 자료를 보고 적용해야겠다 싶었다. 그래서 [trusktr/DefaultKeyBinding.dict](https://gist.github.com/trusktr/1e5e516df4e8032cbc3d) 을 보고 적용했다.<br/>



\~/Library 디렉터리 내에 KeyBindings 디렉터리를 생성하고 그 안에 `DefaultKeyBinding.dict` 파일을 생성한다.

```bash
$ cd ~/Library
$ mkdir KeyBindings
$ cd KeyBindings
$ touch DefaultKeyBinding.dict
```

<br/>

DefaultKeyBinding.dict 파일을 열어서 [trusktr/DefaultKeyBinding.dict](https://gist.github.com/trusktr/1e5e516df4e8032cbc3d) 의 내용중 필요한 부분만을 추려서 아래와 같이 복사해서 붙여넣은 후 저장해준다.

```plain
/*
NOTE: typically the Windows 'Insert' key is mapped to what Macs call 'Help'.
Regular Mac keyboards don't even have the Insert key, but provide 'Fn' instead,
which is completely different.
Reference
- https://gist.github.com/trusktr/1e5e516df4e8032cbc3d
- https://andrewpage.tistory.com/71
*/

{
    "@\UF72B"  = "moveToEndOfDocument:";                         /* Cmd  + End   */
    "~@\UF703" = "moveToEndOfDocument:";                         /* Cmd + Option + Right Arrow */

    "@$\UF72B" = "moveToEndOfDocumentAndModifySelection:";       /* Shift + Cmd  + End */

    "@\UF729"  = "moveToBeginningOfDocument:";                   /* Cmd  + Home  */
    "~@\UF702" = "moveToBeginningOfDocument:";                   /* Cmd + Option + Left Arrow */

    "@$\UF729" = "moveToBeginningOfDocumentAndModifySelection:"; /* Shift + Cmd  + Home */

    "\UF729"   = "moveToBeginningOfLine:";                       /* Home         */
    "~\UF702"  = "moveToBeginningOfLine:";                       /* Option + Left Arrow */

    "$\UF729"  = "moveToBeginningOfLineAndModifySelection:";     /* Shift + Home */
    "$~\UF702" = "moveToBeginningOfLineAndModifySelection:";     /* Shift + Option + Right Arrow */

    "\UF72B"   = "moveToEndOfLine:";                             /* End          */
    "~\UF703"  = "moveToEndOfLine:";                             /* Option + Right Arrow */

    "$\UF72B"  = "moveToEndOfLineAndModifySelection:";           /* Shift + End  */
    "$~\UF703" = "moveToEndOfLineAndModifySelection:";           /* Shift + Option + Left Arrow  */

    "\UF72C"   = "pageUp:";                                      /* PageUp       */
    "\UF72D"   = "pageDown:";                                    /* PageDown     */
    "$\UF728"  = "cut:";                                         /* Shift + Del  */
    "$\UF727"  = "paste:";                                       /* Shift + Ins */
    "@\UF727"  = "copy:";                                        /* Cmd  + Ins  */
    "$\UF746"  = "paste:";                                       /* Shift + Help */
    "@\UF746"  = "copy:";                                        /* Cmd  + Help (Ins) */

    "~j"       = "moveBackward:";                                /* Option + j */
    "~l"       = "moveForward:";                                 /* Option + l */
    "~i"       = "moveUp:";                                      /* Option + i */
    "~k"       = "moveDown:";                                    /* Option + k */

    "@~i"      = ("moveUp:","moveUp:","moveUp:","moveUp:","moveUp:","moveUp:","moveUp:","moveUp:",);                            /* Cmd + Option + j */
    "@~k"      = ("moveDown:","moveDown:","moveDown:","moveDown:","moveDown:","moveDown:","moveDown:","moveDown:",);                            /* Cmd + Option + j */

    "@\UF702"  = "moveWordBackward:";                            /* Cmd  + LeftArrow */
    "@~j"      = "moveWordBackward:";                            /* Cmd + Option + j */
    "@\U007F"  = "deleteWordBackward:";                          /* Cmd  + Backspace */

    "@\UF703"  = "moveWordForward:";                             /* Cmd  + RightArrow */
    "@~l"      = "moveWordForward:";                             /* Cmd + Option + l */
    "@\UF728"  = "deleteWordForward:";                           /* Cmd  + Delete */

    "@$\UF702" = "moveWordBackwardAndModifySelection:";          /* Shift + Cmd  + Leftarrow */
    "@$\UF703" = "moveWordForwardAndModifySelection:";           /* Shift + Cmd  + Rightarrow */
}
```







