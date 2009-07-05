package languish.parsing;

import java.util.List;


import org.quenta.tedir.antonius.doc.ITextDocument;
import org.quenta.tedir.antonius.message.IMessage;

public class ParserUtil {

  public static void failWithInternalMessages(List<IMessage> messages,
      ITextDocument doc) {
    if (!messages.isEmpty()) {
      throw new InternalParsingError("Error(s): "
          + formatMessages(messages, doc));
    }
  }

  public static void failWithExternalMessages(List<IMessage> messages,
      ITextDocument doc) {
    if (!messages.isEmpty()) {
      throw new ExternalParsingError("Error(s): "
          + formatMessages(messages, doc));
    }
  }

  public static String formatMessages(List<IMessage> messages, ITextDocument doc) {
    StringBuilder sb = new StringBuilder();

    for (IMessage message : messages) {
      int startLine =
          doc.getLineManager().getLineOfOffset(
              message.getPosition().getStartOffset());
      int startCol =
          doc.getLineManager().getColumnOfOffset(
              message.getPosition().getStartOffset());
      int endLine =
          doc.getLineManager().getLineOfOffset(
              message.getPosition().getEndOffset());
      int endCol =
          doc.getLineManager().getColumnOfOffset(
              message.getPosition().getEndOffset());

      sb.append(message.getMessage()) //
          .append(" at ") //
          .append(startLine).append(":").append(startCol) //
          .append(" - ") //
          .append(endLine).append(":").append(endCol) //
          .append('\n');
    }

    return sb.toString();
  }

}
