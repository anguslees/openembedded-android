require nandlogical_${PV}.bb

FILESPATHPKG =. "nandlogical:"

inherit klibc

do_compile() {
        ${CC} ${CFLAGS} ${LDFLAGS} -static -I${STAGING_INCDIR} nandlogical.c -o nandlogical
}
