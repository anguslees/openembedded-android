inherit sdk

DEPENDS = "virtual/${TARGET_PREFIX}binutils virtual/${TARGET_PREFIX}libc-for-gcc"

do_compile_prepend() {
	# Need to avoid build CPP/CPPFLAGS polluting target CPP/CPPFLAGS.
	unset CPP
	unset CPPFLAGS
}
