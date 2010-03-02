DESCRIPTION = "Target package for Android SDK"
ALLOW_EMPTY = "1"

LIBC_PKGS_android = "bionic bionic-dev bionic-thread-db"

RDEPENDS_${PN} += "\
    task-sdk-bare \
    bionic-dev \
    zlib-dev \
    "
