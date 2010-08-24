DESCRIPTION = "FFTW benchmarks"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"

# single precision fftw is called fftwf 
DEPENDS = "fftwf"

SRC_URI = "http://www.fftw.org/benchfft/benchfft-${PV}.tar.gz"

EXTRA_OECONF = "--disable-fortran --enable-single --enable-shared"

inherit autotools pkgconfig

do_compile_prepend() {
	sed -i -e 's:all-recursive:$(RECURSIVE_TARGETS):g' ${S}/Makefile
}

SRC_URI[md5sum] = "9356e5e9dcb3f1481977009720a2ccf8"
SRC_URI[sha256sum] = "1b4a5b5e48ad5e61a21586b7b59d5c0a88691a981e73e2c6dc5868197461791b"
