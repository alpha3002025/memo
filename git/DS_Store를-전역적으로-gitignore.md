
macOS에서 .DS_Store 파일 생성을 전역적으로 무시하려면:
<br/>


# 전역 .gitignore 설정 (선택사항)
```
echo ".DS_Store" >> ~/.gitignore_global
git config --global core.excludesfile ~/.gitignore_global
```
<br/>

