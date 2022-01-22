package com.pattern;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	// throws : 예외처리에 대한 부분을 던지겠다!(넘겨준다)
}
