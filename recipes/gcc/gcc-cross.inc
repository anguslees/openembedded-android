inherit cross

DEPENDS = "virtual/${TARGET_PREFIX}binutils virtual/${TARGET_PREFIX}libc-for-gcc ${NATIVEDEPS}"
PROVIDES = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++"

do_compile_prepend() {
	# Need to avoid build CPP/CPPFLAGS polluting target CPP/CPPFLAGS.
	unset CPP
	unset CPPFLAGS
}

do_stage () {
    use_do_install_for_stage
}

require gcc-configure-cross.inc
require gcc-package-cross.inc
