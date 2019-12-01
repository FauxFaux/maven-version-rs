In the root:
```
cd ../nexers
make
echo 'select distinct version from versions' | sqlite3 maven.db | sort -V > $OLDPWD/tests/versions.lst
```

In this dir:
```
just
```
